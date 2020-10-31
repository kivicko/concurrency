package com.kivilcimeray.crackthebomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threadList = new ArrayList<>();

        threadList.add(new AscendingThread(vault));
        threadList.add(new DescendingThread(vault));
        threadList.add(new RandomThread(vault));
        threadList.add(new BombThread());

        for(Thread thread : threadList) {
            thread.start();
        }

    }

    private static class Vault {
        private final int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return this.password == guess;
        }
    }

    private static class BombThread extends Thread {
        @Override
        public void run() {
            System.out.println("!! Find the code to stop counter !!");
            for (int cd = 10; cd > 0; cd--) {
                try {
                    Thread.sleep(1000);
                    System.out.println(cd);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("BOOOOM! ");
            System.exit(0);
        }
    }

    private static abstract class CrackThread extends Thread {
        protected Vault vault;

        public CrackThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            System.out.println("Starting thread : " + this.getName());
            super.start();
        }
    }

    private static class AscendingThread extends CrackThread {

        public AscendingThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_PASSWORD; i++) {
                if (this.vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " found the code. Code : " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingThread extends CrackThread {

        public DescendingThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = MAX_PASSWORD; i > 0; i--) {
                if (this.vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " found the code. Code : " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class RandomThread extends CrackThread {

        public RandomThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            Random random = new Random();

            while (true) {
                int i = random.nextInt(MAX_PASSWORD);
                if (vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " found the code. Code : " + i);
                    System.exit(0);
                }
            }
        }
    }
}