package com.kivilcimeray.deadlocktrain;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        TrainIntersaction intersaction = new TrainIntersaction();
        Thread trainAThread = new Thread(new TrainA(intersaction));
        Thread trainBThread = new Thread(new TrainB(intersaction));

        trainBThread.start();
        trainAThread.start();
    }

    public static class TrainA implements Runnable {
        TrainIntersaction intersaction;
        Random random = new Random();

        public TrainA(TrainIntersaction intersaction) {
            this.intersaction = intersaction;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTıme = random.nextInt(5);

                try {
                    Thread.sleep(sleepingTıme);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersaction.takeRoadA();

            }
        }
    }

    public static class TrainB implements Runnable {
        TrainIntersaction intersaction;
        Random random = new Random();

        public TrainB(TrainIntersaction intersaction) {
            this.intersaction = intersaction;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTıme = random.nextInt(5);

                try {
                    Thread.sleep(sleepingTıme);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersaction.takeRoadB();

            }
        }
    }

    public static class TrainIntersaction {
        Object lockA = new Object();
        Object lockB = new Object();

        public void takeRoadA() {
            synchronized (lockA) {
                System.out.println("Lock A for takeRoadA granted by Thead : " + Thread.currentThread().getName());
                synchronized (lockB) {
                    System.out.println("Lock B for takeRoadA granted by Thead : " + Thread.currentThread().getName());

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }



                }
            }
        }
        public void takeRoadB() {
            synchronized (lockB) {
                System.out.println("Lock B for takeRoadB granted by Thead : " + Thread.currentThread().getName());
                synchronized (lockA) {
                    System.out.println("Lock A for takeRoadB granted by Thead : " + Thread.currentThread().getName());

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }



                }
            }
        }
    }
}
