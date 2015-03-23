package com.github.michaelkhw.playground.pi.msg;

import java.io.Serializable;

/**
 * Created by michael on 22/3/15.
 */
public class CalcResultMsg implements Serializable {
    private final double pi;

    public CalcResultMsg(double pi) {
        this.pi = pi;
    }

    public double getPi() {
        return pi;
    }
}