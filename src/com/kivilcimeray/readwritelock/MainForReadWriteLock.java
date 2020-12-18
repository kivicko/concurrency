package com.kivilcimeray.readwritelock;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainForReadWriteLock {
    public static final int HIGHEST_VALUE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase database = new InventoryDatabase();
        Random random = new Random();

        for(int i = 0 ; i < 100000; i++) {
            database.addItem(random.nextInt(HIGHEST_VALUE));
        }

        Thread writer = new Thread(() -> {
            while(true) {
                database.addItem(random.nextInt(HIGHEST_VALUE));
                database.removeItem(random.nextInt(HIGHEST_VALUE));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();

        for( int readerIndex = 0; readerIndex< numberOfReaderThreads; readerIndex++) {
            Thread reader = new Thread(() -> {
               for(int i = 0 ; i < 100000; i++) {
                   int upperBoundPrice = random.nextInt(HIGHEST_VALUE);
                   int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
                   database.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
               }
            });
            reader.setDaemon(true);
            readers.add(reader);
        }

        long timeStamp = System.currentTimeMillis();

        for(Thread reader : readers) {
            reader.start();
        }

        for(Thread reader : readers) {
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();

        System.out.printf("Reading took %d ms.", endReadingTime - timeStamp);
    }

    public static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            reentrantLock.readLock().lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);

                Integer toKey = priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;
                for (int numberOfItemsForPrice : rangeOfPrices.values()) {
                    sum += numberOfItemsForPrice;
                }

                return sum;
            } finally {
                reentrantLock.readLock().unlock();
            }

        }

        public void addItem(int price) {
            reentrantLock.writeLock().lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null) {
                    priceToCountMap.put(price, 1);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice + 1);
                }
            } finally {
                reentrantLock.writeLock().unlock();
            }
        }

        public void removeItem(int price) {
            reentrantLock.writeLock().lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(price);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }
            } finally {
                reentrantLock.writeLock().unlock();
            }
        }
    }
}
