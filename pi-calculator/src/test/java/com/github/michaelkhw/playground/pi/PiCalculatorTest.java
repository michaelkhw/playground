package com.github.michaelkhw.playground.pi;

import com.github.michaelkhw.playground.timer.NanoTimer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by michael on 21/3/15.
 */
@RunWith(Parameterized.class)
public class PiCalculatorTest {
    private PiCalculator calculator;

    @Parameterized.Parameters(name = "{index}: PiCalculatorTest({0})")
    public static Collection<Object[]> testParameters() {
        return Arrays.asList(
                new Object[][]{
                        {"Akka", new AkkaPiCalculator()},
                        {"Simple", new SimplePiCalculator()},
                        {"Native", new NativePiCalculator()}
                });
    }

    public PiCalculatorTest(String name, PiCalculator calculator) {
        this.calculator = calculator;
    }

    @Test
    public void testCalculate() throws Exception {
        try {
            // Put a heavy Load to the calculator, attempting to JIT compile the method
            calculator.calculate(10000);

            // The actual work begins
            double actual;
            // This will took around 1-2 secs on my mac
            try (NanoTimer ignored = new NanoTimer()) {
                actual = calculator.calculate(100000000L);
            }

            assertEquals(3.14159, actual, 0.00001);
        } finally {
            if (calculator instanceof AutoCloseable)
                ((AutoCloseable) calculator).close();
        }
    }
}
