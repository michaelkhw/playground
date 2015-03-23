package com.github.michaelkhw.playground.pi.msg;

/**
 * Created by michael on 22/3/15.
 */
public class IterationCalcRequestMsg {
    private final long start;
    private final long end;

    public IterationCalcRequestMsg(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
