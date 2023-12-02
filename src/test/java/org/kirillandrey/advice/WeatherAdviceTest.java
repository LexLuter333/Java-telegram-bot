package org.kirillandrey.advice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAdviceTest {

    @Test
    void testRainAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Rain");
        String advice = WeatherAdvice.weathAdvice(weatherConditions);
        assertEquals("- Сегодня возможен дождь, советуем взять зонтик.", advice);
    }

    @Test
    void testSnowAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Snow");
        String advice = WeatherAdvice.weathAdvice(weatherConditions);
        assertEquals("- Сегодня будет снег, будьте аккуратнее.", advice);
    }

    @Test
    void testCloudyWeatherAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Clouds");
        String advice = WeatherAdvice.weathAdvice(weatherConditions);
        assertEquals("", advice);
    }

    @Test
    void testClearWeatherAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        weatherConditions.add("Clear");
        String advice = WeatherAdvice.weathAdvice(weatherConditions);
        assertEquals("", advice);
    }

    @Test
    void testEmptyWeatherAdvice() {
        ArrayList<String> weatherConditions = new ArrayList<>();
        String advice = WeatherAdvice.weathAdvice(weatherConditions);
        assertEquals("", advice);
    }

    @Test
    void testNullWeatherAdvice() {
        String advice = WeatherAdvice.weathAdvice(null);
        assertEquals("", advice);
    }
}
