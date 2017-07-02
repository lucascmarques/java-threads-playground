package com.lmarques.javathreads;

public class WaitNotifySample {

    static class Processor {

        public void produce() throws InterruptedException {
            synchronized (this) {
                System.out.println("We are in the producer method...");
                wait(10000);
                System.out.println("We are in the producer method again...");
            }
        }

        public void consume() throws InterruptedException {
            synchronized (this) {
                System.out.println("We are in the consumer method...");
                notify();
//            notifyAll(); notify all threads that are waiting for this lock
                Thread.sleep(3000); // java is going to wait this before notifying the producer
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
