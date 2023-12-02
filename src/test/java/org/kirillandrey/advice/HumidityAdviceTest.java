package org.kirillandrey.advice;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumidityAdviceTest {

    @Test
    void testLowHumidityAdvice() {
        String advice = HumidityAdvice.humAdvice(25);
        assertEquals("- Сегодня низкая влажность воздуха, пейте больше воды.", advice);
    }

    @Test
    void testStandardHumidityAdvice() {
        String advice = HumidityAdvice.humAdvice(50);
        assertEquals("- Сегодня стандартный уровень влажности, ваш день должен быть отличным.", advice);
    }

    @Test
    void testHighHumidityAdvice() {
        String advice = HumidityAdvice.humAdvice(75);
        assertEquals("- Сегодня высокая влажность воздуха, пейте больше воды.", advice);
    }

    @Test
    void testNullHumidityAdvice() {
        String advice = HumidityAdvice.humAdvice(null);
        assertEquals("", advice);
    }
}
