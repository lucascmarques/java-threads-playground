package com.lmarques.javathreads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LocksSample {

    private static int counter = 0;
    private static Lock lock = new ReentrantLock();

    public static void increment() {
        lock.lock();
        try {
            IntStream.range(0, 1000).forEach(i -> counter++);
        } finally {
            lock.unlock(); // avoid deadlock
        }
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> increment());
        Thread thread2 = new Thread(() -> increment());
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter is " + counter);

    }

}
