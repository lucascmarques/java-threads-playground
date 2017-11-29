package com.lmarques.javathreads.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult1;

import java.text.SimpleDateFormat;
import java.util.Date;

@JCStressTest
@State
@Description("Concurrency test for SimpleDateFormat")
@Outcome(id = "-1", desc = "An exception occurred", expect = Expect.ACCEPTABLE_INTERESTING)
@Outcome(id = "1", desc = "Correct behaviour", expect = Expect.ACCEPTABLE)
public class SimpleDateFormatTest {

    private static final Date DATE = new Date();
    private SimpleDateFormat format = new SimpleDateFormat();

    @Actor
    public void actor1() {
        try {
            format.format(DATE);
        }catch (Throwable e) {
        }
    }

    @Actor
    public void actor2(IntResult1 result) {
        try {
            format.format(DATE);
            result.r1 = 1;
        }catch (Throwable e) {
            result.r1 = -1;
        }
    }

}
