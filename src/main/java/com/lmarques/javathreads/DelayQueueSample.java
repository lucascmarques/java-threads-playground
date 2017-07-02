package com.lmarques.javathreads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueSample {

    static class DelayedWorker implements Delayed {

        private Long duration;
        private String message;

        public DelayedWorker(long duration, String message) {
            this.duration = duration;
            this.message = message;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed otherDelayed) {
            return this.duration.compareTo(((DelayedWorker) otherDelayed).getDuration());
        }

        @Override
        public String toString() {
            return this.getMessage();
        }

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public static void main(String[] args) {

        BlockingQueue<DelayedWorker> blockingQueue = new DelayQueue<>();
        try {
            blockingQueue.put(new DelayedWorker(1000, "This is the first message..."));
            blockingQueue.put(new DelayedWorker(10000, "This is the second message..."));
            blockingQueue.put(new DelayedWorker(4000, "This is the third message..."));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!blockingQueue.isEmpty()) {
            try {
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
