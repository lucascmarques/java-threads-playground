package com.lmarques.javathreads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueSample {

    static class FirstWorker implements Runnable {

        private BlockingQueue<Integer> blockingQueue;

        public FirstWorker(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            int counter = 0;
            while (true) {
                try {
                    System.out.println("Putting items to the queue... " + counter);
                    blockingQueue.put(counter++);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class SecondWorker implements Runnable {

        private BlockingQueue<Integer> blockingQueue;

        public SecondWorker(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Integer number = blockingQueue.take();
                    System.out.println("Taking items to the queue... " + number);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);
        new Thread(new FirstWorker(blockingQueue)).start();
        new Thread(new SecondWorker(blockingQueue)).start();
    }

}
