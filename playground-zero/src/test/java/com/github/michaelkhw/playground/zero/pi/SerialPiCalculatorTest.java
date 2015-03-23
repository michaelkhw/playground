package com.github.michaelkhw.playground.zero.pi;

import com.github.michaelkhw.playground.zero.NanoTimer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michael on 21/3/15.
 */
public class SerialPiCalculatorTest {
    @Test
    public void testCalculate() {
        SerialPiCalculator calculator = new SerialPiCalculator();
        // Put a heavy Load to the calculator
        calculator.calculate(10000000);

        // The actual work begins
        double actual;
        // This will took almost 1 sec on my mac
        try (NanoTimer ignored = new NanoTimer()) {
            actual = calculator.calculate(1000000000L);
        }

        assertEquals(3.14159, actual, 0.00001);
    }
}