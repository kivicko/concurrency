package com.kivilcimeray.datarace;

public class SharedResourceWithThreadClasses {

    public static void main(String[] args) {
        SharedData sharedData = new SharedData();

        IncrementThread incrementThread1 = new IncrementThread(sharedData);
        ControlThread controlThread1 = new ControlThread(sharedData);

        incrementThread1.start();
        controlThread1.start();
    }


}

class IncrementThread extends Thread {
    SharedData sh;

    public IncrementThread(SharedData sh) {
        this.sh = sh;
    }

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++)
            sh.increment();
    }
}

class ControlThread extends Thread {
    SharedData sh;

    public ControlThread(SharedData sh) {
        this.sh = sh;
    }

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++)
            sh.controll();
    }
}

class SharedData {
    private int x = 0;
    private int y = 0;

    public void increment() {
        x++;
        y++;
    }

    public void controll() {
        if(y > x) {
            throw new RuntimeException("datarace occured");
        }
    }
}