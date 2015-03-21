package com.github.michaelkhw.playground.zero.pi;

/**
 * Created by michael on 21/3/15.
 */
public class SerialPiCalculator implements PiCalculator {
    @Override
    public double calculate(final int iterations, final int stepsPerIteration) {
        double t = 0;

        final int ttlIterations = iterations * stepsPerIteration * 2;

        for (int i = 3; i <= ttlIterations; i = i + 4) {
            t = t - (1.0d / i) + (1.0d / (i + 2));
        }

        return (1 + t) * 4;
    }
}
