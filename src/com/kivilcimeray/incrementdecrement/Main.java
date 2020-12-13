package com.kivilcimeray.incrementdecrement;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException { //2000-3500 vs 11-15
        InventoryCounter inventoryCounter = new InventoryCounter();

        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        long start = System.currentTimeMillis();

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();


        long end = System.currentTimeMillis();
        System.out.println("current count : " + inventoryCounter.getCount());
        System.out.println("the time is : " + (end-start));
    }

    static class DecrementingThread extends Thread {

        private final InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i = 0; i< 99999999; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    static class IncrementingThread extends Thread {

        private final InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i = 0; i< 99999999; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int count = 0;
        //private final AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            //count.incrementAndGet();
            count++;
        }

        public void decrement() {
            //count.decrementAndGet();
            count--;
        }

        public int getCount() {
            return count;
        }
    }
}
