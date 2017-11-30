package com.lmarques.javathreads.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult1;

@JCStressTest
@State
@Description("Test if unsafe publication is unsafe.")
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "The object is not yet published")
@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "The object is published, but all fields are 0")
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "The object is published, at least 1 field is visible")
@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "The object is published, at least 2 field is visible")
@Outcome(id = "3", expect = Expect.ACCEPTABLE, desc = "The object is published, at least 3 field is visible")
@Outcome(id = "4", expect = Expect.ACCEPTABLE, desc = "The object is published, all fields are visible")
public class UnsafePublication {

    int number = 1;

    UnsafePublish toPublish;

    @Actor
    public void publish() {
        this.toPublish = new UnsafePublish(number);
    }

    @Actor
    public void consume(IntResult1 result) {
        UnsafePublish localToPublish = toPublish;
        if (localToPublish != null) {
            result.r1 = localToPublish.f1 + localToPublish.f2 + localToPublish.f3 + localToPublish.f4;
        } else {
            result.r1 = -1;
        }
    }

    static class UnsafePublish {
        int f1, f2, f3, f4;
        public UnsafePublish(int number) {
            this.f1 = number;
            this.f2 = number;
            this.f3 = number;
            this.f4 = number;
        }
    }
}
