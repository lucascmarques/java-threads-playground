package com.lmarques.javathreads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CountDownLatchSample {

    static class Worker implements Runnable {

        private int id;
        private CountDownLatch countDownLatch;

        public Worker(int id, CountDownLatch countDownLatch) {
            this.id = id;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            doWork();
            countDownLatch.countDown();
        }

        private void doWork() {
            System.out.println("Thread with id " + id + " starts working...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        IntStream.range(0, 10).forEach((i) -> {
            executorService.execute(new Worker(i + 1, countDownLatch));
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All the prerequisites are done.");
        executorService.shutdown();

    }

}
