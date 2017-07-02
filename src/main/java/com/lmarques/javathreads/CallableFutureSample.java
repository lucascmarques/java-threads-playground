package com.lmarques.javathreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CallableFutureSample {

    static class Processor implements Callable<String> {

        private int id;

        public Processor(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return "Id: " + id;
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<String>> futures = new ArrayList<>();

        IntStream.range(0, 5).forEach((i) -> {
            Future<String> future = executorService.submit(new Processor(i + 1));
            futures.add(future);
        });

        futures.stream().forEach((future) -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

    }

}
