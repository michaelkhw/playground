package com.github.michaelkhw.playground.pi;

/**
 * Created by michael on 21/3/15.
 */
public class SimplePiCalculator implements PiCalculator {
    @Override
    public double calculate(final long iterations) {
        return calculateCore(0, iterations);
    }

    /**
     * Calculate Pi.
     *
     * @param fromIteration - From iteration index (starts from 0)
     * @param toIteration - To iteration index (exclusive)
     * @return
     */
    static double calculateCore(final long fromIteration, final long toIteration)
    {
        double t = 0;

        long idxStart = fromIteration * 4 + 1;
        long idxEnd = toIteration * 4 - 3;

        for (long idx = idxStart; idx <= idxEnd ; idx += 4) {
            t += 1.0d / idx - 1.0d / (idx + 2);
        }

        return t * 4;
    }
}
