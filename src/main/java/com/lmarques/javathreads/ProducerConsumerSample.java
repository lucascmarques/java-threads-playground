package com.lmarques.javathreads;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerSample {

    static class Processor {

        private List<Integer> list = new ArrayList<Integer>();
        private final int LIMIT = 5;
        private final int BOTTOM = 0;
        private final Object lock = new Object();
        private int value = 0;

        public void produce() throws InterruptedException {
            synchronized (lock) {
                while(true) {
                    if (list.size() == LIMIT) {
                        System.out.println("Waiting for removing itens from the list...");
                        lock.wait();
                    } else {
                        System.out.println("Adding: " + value);
                        list.add(value++);
                        lock.notify();
                    }
                    Thread.sleep(500);
                }
            }
        }

        public void consume() throws InterruptedException {
            synchronized (lock) {
                while(true) {
                    if (list.size() == BOTTOM) {
                        System.out.println("Waiting for adding itens to the list...");
                        lock.wait();
                    } else {
                        System.out.println("Removing: " + list.remove(--value));
                        lock.notify();
                    }
                    Thread.sleep(500);
                }
            }
        }

    }

    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread thread1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }

}
