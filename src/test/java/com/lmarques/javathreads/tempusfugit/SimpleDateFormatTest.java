package com.lmarques.javathreads.tempusfugit;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import net.jodah.concurrentunit.Waiter;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleDateFormatTest {

    public static final int NUMBER_OF_THREADS = 4;
    public static final int TIMEOUT = 10000;
    public static final int NUMBER_OF_ITERATIONS = 10000;

    @Rule
    public ConcurrentRule concurrentRule = new ConcurrentRule();

    @Rule
    public RepeatingRule repeatingRule = new RepeatingRule();

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Test(timeout = TIMEOUT)
    @Concurrent(count = NUMBER_OF_THREADS)
    @Repeating(repetition = NUMBER_OF_ITERATIONS)
    public void testSimpleDateFormatThreadSafety() throws ParseException {
        String source = "2012-10-26";
        Date date = format.parse(source);
        String reformatted = format.format(date);
        if (!reformatted.equals(source)) {
            Assert.fail(source + "!=" + reformatted);
        }
    }

}
