package org.kirillandrey.WeatherBot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherParseTest {

    @Test
    public void testGetReadyForecast() {
        // Тест на успешное получение прогноза для существующего города
        String city = "Moscow";
        String forecast = WeatherParse.getReadyForecast(city);
        assertNotNull(forecast);
        assertFalse(forecast.isEmpty());
    }

    @Test
    public void testDownloadJsonRawData() {
        // Тест на успешное скачивание JSON данных для существующего города
        String city = "Moscow";
        try {
            String jsonRawData = WeatherParse.downloadJsonRawData(city);
            assertNotNull(jsonRawData);
            assertFalse(jsonRawData.isEmpty());
        } catch (Exception e) {
            fail("Exception should not be thrown for a valid city");
        }
    }

    @Test
    public void testParsePojo() {
        // Тест на успешное парсинг JSON данных
        String json = "{\"city\": {\"name\": \"Moscow\"}, \"list\": []}";
        String city = "Moscow";
        try {
            String parsedData = WeatherParse.parsePojo(json, city);
            assertNotNull(parsedData);
            assertFalse(parsedData.isEmpty());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid JSON data");
        }
    }

    @Test
    public void testFormatForecastData() {
        // Тест на успешное форматирование данных прогноза
        String dateTime = "2023-11-06 12:00:00";
        String description = "Clear";
        double temperature = 25.5;

        try {
            String formattedData = WeatherParse.formatForecastData(dateTime, description, temperature);
            assertNotNull(formattedData);
            assertFalse(formattedData.isEmpty());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid data");
        }
    }
}

