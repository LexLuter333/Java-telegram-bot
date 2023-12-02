package org.kirillandrey.advice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureAdviceTest {

    @Test
    void testHotTemperatureAdvice() {
        String advice = TemperatureAdvice.tempAdvice(29);
        assertEquals("- Сегодня будет жарко, не забывайте надеть головной убор и пейте больше жидкости.", advice);
    }

    @Test
    void testModerateTemperatureAdvice() {
        String advice = TemperatureAdvice.tempAdvice(15);
        assertEquals("- Сегодня хорошая погода для прогулки.", advice);
    }

    @Test
    void testColdTemperatureAdvice() {
        String advice = TemperatureAdvice.tempAdvice(-5);
        assertEquals("- Сегодня очень холодно, советуем очень тепло одеться.", advice);
    }

    @Test
    void testZeroTemperatureAdvice() {
        String advice = TemperatureAdvice.tempAdvice(0);
        assertEquals("- Сегодня прохладно и возможен гололёд, советуем надеть шапку.", advice);
    }

    @Test
    void testNegativeTemperatureAdvice() {
        String advice = TemperatureAdvice.tempAdvice(-10);
        assertEquals("- Сегодня холодно, советуем одеться теплее.", advice);
    }

    @Test
    void testNullTemperatureAdvice() {
        String advice = TemperatureAdvice.tempAdvice(null);
        assertEquals("", advice);
    }
}
