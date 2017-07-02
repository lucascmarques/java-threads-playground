package com.lmarques.javathreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreSample {

    enum Downloader {

        INSTANCE;
        private Semaphore semaphore = new Semaphore(3, true);

        public void downloadData() {
            try {
                semaphore.acquire();
                download();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }

        private void download() {
            System.out.println("Downloading data from the web...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        IntStream.range(1, 12).forEach((i) -> {
            executorService.execute(() -> Downloader.INSTANCE.downloadData());
        });

    }

}
