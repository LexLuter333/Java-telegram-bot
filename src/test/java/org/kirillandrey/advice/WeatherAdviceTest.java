package org.kirillandrey.advice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAdviceTest {
    private WeatherAdvice weatherAdvice = new WeatherAdvice();
    @Test
    void testRainAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Rain");
        String advice = weatherAdvice.weathAdvice(weatherConditions);
        assertEquals("- Сегодня возможен дождь, советуем взять зонтик.", advice);
    }

    @Test
    void testSnowAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Snow");
        String advice = weatherAdvice.weathAdvice(weatherConditions);
        assertEquals("- Сегодня будет снег, будьте аккуратнее.", advice);
    }

    @Test
    void testCloudyWeatherAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Clouds");
        String advice = weatherAdvice.weathAdvice(weatherConditions);
        assertEquals("", advice);
    }

    @Test
    void testClearWeatherAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Clear");
        String advice = weatherAdvice.weathAdvice(weatherConditions);
        assertEquals("", advice);
    }

    @Test
    void testEmptyWeatherAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        String advice = weatherAdvice.weathAdvice(weatherConditions);
        assertEquals("", advice);
    }

    @Test
    void testNullWeatherAdvice() {
        String advice = weatherAdvice.weathAdvice(null);
        assertEquals("", advice);
    }
}
