package org.kirillandrey.WeatherBot;
import org.junit.jupiter.api.Test;
import org.kirillandrey.service.SettingJson;

import static org.junit.jupiter.api.Assertions.*;
import org.kirillandrey.JSON.List;


public class WeatherParseTest {
    private WeatherParse weatherParse = new WeatherParse();

    @Test
    public void testGetReadyForecast() {
        // Тест на успешное получение прогноза для существующего города
        String city = "Moscow";
        String forecast = weatherParse.getReadyForecast(city, new SettingJson(), 1);
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
            String parsedData = WeatherParse.parsePojo(json, city, new SettingJson(),1);
            assertNotNull(parsedData);
            assertFalse(parsedData.isEmpty());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid JSON data");
        }
    }

    @Test
    public void testCheckCity() {
        // Тест на проверку существования города
        String city = "Moscow";
        assertTrue(weatherParse.checkCity(city));
    }

    @Test
    public void testGetListForAlert() {
        // Тест на успешное получение списка для уведомлений
        SettingJson settingJson = new SettingJson();
        settingJson.setCity("Moscow");
        settingJson.setTimezone("3");
        try {
            java.util.List<List> forecastList = weatherParse.getListForAlert(settingJson);
            assertNotNull(forecastList);
            assertFalse(forecastList.isEmpty());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid settings");
        }
    }
}
