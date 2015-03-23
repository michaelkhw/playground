package com.github.michaelkhw.playground.timer;

import java.util.function.LongPredicate;

/**
 * A nano second timer.
 *
 * Created by michael on 21/3/15.
 */
public class NanoTimer implements AutoCloseable {
    public static final LongPredicate DO_SYSOUT = value -> {
        System.out.println(String.format("Operation took %,d ns", value));
        return true;
    };

    private long start;
    private LongPredicate onClosePredicate;

    public NanoTimer() {
        this(DO_SYSOUT);
    }

    public NanoTimer(LongPredicate onClosePredicate) {
        this.onClosePredicate = onClosePredicate;
        this.start = System.nanoTime();
    }

    public NanoTimer start() {
        this.start = System.nanoTime();
        return this;
    }

    @Override
    public void close() {
        long end = System.nanoTime();
        onClosePredicate.test(end - start);
    }
}
