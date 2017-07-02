package com.lmarques.javathreads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerWithLocksSample {

    static class Worker {

        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void producer() throws InterruptedException {
            lock.lock();
            System.out.println("Producer method...");
            condition.await();
            System.out.println("Producer method again...");
            lock.unlock();
        }

        public void consumer() throws InterruptedException {
            lock.lock();
            Thread.sleep(2000);
            System.out.println("Consumder method...");
            condition.signal();
            lock.unlock();
        }

    }

    public static void main(String[] args) {

        Worker worker = new Worker();
        Thread thread1 = new Thread(() -> {
            try {
                worker.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                worker.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
