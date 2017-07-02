package com.lmarques.javathreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ExecutorSample {

    static class Worker implements Runnable {
        @Override
        public void run() {
            IntStream.range(0, 10).forEach((j) -> {
                System.out.println(j);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) {

//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        IntStream.range(0, 15).forEach((i) -> {
//            executorService.submit(new Worker());
//        });

        ExecutorService executorService = Executors.newCachedThreadPool();
        IntStream.range(0, 1000).forEach((i) -> {
            executorService.submit(new Worker());
        });

    }

}
