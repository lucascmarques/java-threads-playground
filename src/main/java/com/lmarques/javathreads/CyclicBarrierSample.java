package com.lmarques.javathreads;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CyclicBarrierSample {

    static class Worker implements Runnable {

        private int id;
        private CyclicBarrier cyclicBarrier;
        private Random random;

        public Worker(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
            this.random = new Random();
        }

        @Override
        public void run() {
            doWork();
        }

        private void doWork() {
            try {
                System.out.println("Thread with ID " + id + " starts the task...");
                Thread.sleep(random.nextInt(3000));
                System.out.println("Thread with ID " + id + " finished...");
                cyclicBarrier.await();
                System.out.println("After await...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "" + this.id;
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("All the tasks are finished");
        });

        IntStream.range(0, 10).forEach((i) -> executorService.execute(new Worker(i + 1, cyclicBarrier)));

        executorService.shutdown();

    }

}
