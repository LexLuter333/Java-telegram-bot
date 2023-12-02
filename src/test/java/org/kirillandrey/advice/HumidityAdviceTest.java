package org.kirillandrey.advice;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumidityAdviceTest {
    private HumidityAdvice humidityAdvice = new HumidityAdvice();
    @Test
    void testLowHumidityAdvice() {
        String advice = humidityAdvice.humAdvice(25);
        assertEquals("- Сегодня низкая влажность воздуха, пейте больше воды.", advice);
    }

    @Test
    void testStandardHumidityAdvice() {
        String advice = humidityAdvice.humAdvice(50);
        assertEquals("- Сегодня стандартный уровень влажности, ваш день должен быть отличным.", advice);
    }

    @Test
    void testHighHumidityAdvice() {
        String advice = humidityAdvice.humAdvice(75);
        assertEquals("- Сегодня высокая влажность воздуха, пейте больше воды.", advice);
    }

    @Test
    void testNullHumidityAdvice() {
        String advice = humidityAdvice.humAdvice(null);
        assertEquals("", advice);
    }
}
