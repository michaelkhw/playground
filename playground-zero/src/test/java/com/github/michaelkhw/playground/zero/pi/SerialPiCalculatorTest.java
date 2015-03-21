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
        calculator.calculate(10000, 10000);

        double actual;
        // This will took almost 1 sec on my mac
        try (NanoTimer ignored = new NanoTimer()) {
            actual = calculator.calculate(10000, 10000);
        }

        assertEquals(3.14159, actual, 0.00001);
    }
}
