package org.kirillandrey.advice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureAdviceTest {
    private TemperatureAdvice temperatureAdvice = new TemperatureAdvice();
    @Test
    void testHotTemperatureAdvice() {
        String advice = temperatureAdvice.tempAdvice(29);
        assertEquals("- Сегодня хорошая прогулка для прогулки.", advice);
    }

    @Test
    void testModerateTemperatureAdvice() {
        String advice = temperatureAdvice.tempAdvice(15);
        assertEquals("- Сегодня будет прохладно, советуем надеть ветровку.", advice);
    }

    @Test
    void testColdTemperatureAdvice() {
        String advice = temperatureAdvice.tempAdvice(-5);
        assertEquals("- Сегодня прохладно и возможен гололёд, советуем надеть шапку.", advice);
    }

    @Test
    void testZeroTemperatureAdvice() {
        String advice = temperatureAdvice.tempAdvice(0);
        assertEquals("- Сегодня прохладно и возможен гололёд, советуем надеть шапку.", advice);
    }

    @Test
    void testNegativeTemperatureAdvice() {
        String advice = temperatureAdvice.tempAdvice(-10);
        assertEquals("- Сегодня прохладно, но если одеться теплее, то это хорошая погода для прогулки.", advice);
    }

    @Test
    void testNullTemperatureAdvice() {
        String advice = temperatureAdvice.tempAdvice(null);
        assertEquals("", advice);
    }
}
