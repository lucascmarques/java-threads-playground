package com.lmarques.javathreads.concurrentunit;

import net.jodah.concurrentunit.Waiter;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleDateFormatTest {

    public static final int NUMBER_OF_THREADS = 4;
    public static final int TIMEOUT = 10000;

    private class DateFormatStresser implements Runnable {

        private SimpleDateFormat format;
        private Waiter waiter;

        public DateFormatStresser(SimpleDateFormat format, Waiter waiter) {
            this.format = format;
            this.waiter = waiter;
        }

        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    String source = "2012-10-26";
                    Date date = format.parse(source);
                    String reformatted = format.format(date);
                    if (!reformatted.equals(source)) {
                        waiter.fail(source + "!=" + reformatted);
                    }
                }
                waiter.resume();
            } catch (Throwable e) {
                waiter.fail(e);
            }
        }

    }

    @Test
    public void testSimpleDateFormatThreadSafety() throws Throwable {
        Waiter waiter = new Waiter();
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        List<DateFormatStresser> stressers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            stressers.add(new DateFormatStresser(format, waiter));
        }
        for (DateFormatStresser stresser : stressers) {
            threads.add(new Thread(stresser));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        waiter.await(TIMEOUT, NUMBER_OF_THREADS);
    }

}
