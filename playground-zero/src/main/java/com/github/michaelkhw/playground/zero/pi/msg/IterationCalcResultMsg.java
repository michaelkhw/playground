package com.github.michaelkhw.playground.zero.pi.msg;

import java.io.Serializable;

/**
 * Created by michael on 22/3/15.
 */
public class IterationCalcResultMsg implements Serializable {
    private final double value;

    public IterationCalcResultMsg(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
