package com.kivilcimeray.datarace;

public class SharedClassExample {

    public static void main(String[] args) {
        SharedClass sh = new SharedClass();
        Thread incrementer = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++) {
                sh.increment();
            }
        });

        Thread controller = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i ++) {
                sh.thisShouldNeverHappen();
            }
        });

        incrementer.start();
        controller.start();

    }

    public static class SharedClass {
        private int x = 0;
        private int y = 0;

        //volatile private int x = 0;
        //volatile private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void thisShouldNeverHappen() {
            if (y > x) {
                throw new RuntimeException("our x is : " + x + ", where y is : " + y);
            }
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
