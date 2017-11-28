package com.lmarques.javathreads.testing;

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

        private volatile Throwable exception;
        private SimpleDateFormat format;

        public DateFormatStresser(SimpleDateFormat format) {
            this.format = format;
        }

        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    String source = "2012-10-26";
                    Date date = format.parse(source);
                    String reformatted = format.format(date);
                    if (!reformatted.equals(source)) {
                        Assert.fail(source + "!=" + reformatted);
                    }
                }
            }
            catch (Throwable e) {
                this.exception = e;
            }
        }

        public Throwable getException() {
            return exception;
        }

    }

    @Test
    public void testSimpleDateFormatThreadSafety() throws Throwable {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        List<DateFormatStresser> stressers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            stressers.add(new DateFormatStresser(format));
        }
        for (DateFormatStresser stresser : stressers) {
            threads.add(new Thread(stresser));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join(TIMEOUT);
        }
        for (DateFormatStresser stresser : stressers) {
            if (stresser.getException() != null) {
                throw stresser.getException();
            }
        }
    }

}
