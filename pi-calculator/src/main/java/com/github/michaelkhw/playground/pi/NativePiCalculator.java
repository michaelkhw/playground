package com.github.michaelkhw.playground.pi;

/**
 * Created by michael on 23/3/15.
 */
public class NativePiCalculator implements PiCalculator {

    @Override
    public native double calculate(long iterations);
}
