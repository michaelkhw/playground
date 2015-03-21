package com.github.michaelkhw.playground.zero;

import java.util.function.LongPredicate;

/**
 * Created by michael on 21/3/15.
 */
public class MillisTimer implements AutoCloseable {
    public static final LongPredicate SYSOUT_ON_CLOSE = new LongPredicate() {
        @Override
        public boolean test(long value) {
            System.out.println(String.format("Operation took %d ms", value));
            return true;
        }
    };

    private long start;
    private LongPredicate onClosePredicate;

    public MillisTimer() {
        this(SYSOUT_ON_CLOSE);
    }

    public MillisTimer(LongPredicate onClosePredicate) {
        this.onClosePredicate = onClosePredicate;
        this.start = System.currentTimeMillis();
    }

    public MillisTimer start() {
        this.start = System.currentTimeMillis();
        return this;
    }

    @Override
    public void close() {
        long end = System.currentTimeMillis();
        onClosePredicate.test(end - start);
    }
}
