package com.github.michaelkhw.playground.zero.pi.msg;

import java.io.Serializable;

/**
 * Created by michael on 22/3/15.
 */
public class CalcRequestMsg implements Serializable {
    private final long iterations;

    public CalcRequestMsg(long iterations) {
        this.iterations = iterations;
    }

    public long getIterations() {
        return iterations;
    }
}