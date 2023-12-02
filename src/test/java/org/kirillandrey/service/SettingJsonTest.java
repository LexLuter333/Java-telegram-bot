package org.kirillandrey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
public class SettingJsonTest {

    @Test
    public void testDefaultValues() {
        SettingJson settingJson = new SettingJson();

        // Проверяем, что значения по умолчанию установлены корректно
        assertEquals("Вкл", settingJson.getTemperature());
        assertEquals("Вкл", settingJson.getPressure());
        assertEquals("Вкл", settingJson.getHumidity());
        assertEquals("Вкл", settingJson.getWeather());
        assertEquals("Вкл", settingJson.getWind());
        assertEquals("Не задан", settingJson.getCity());
        assertEquals("Выкл", settingJson.getNotifications());
        assertEquals("Не задано", settingJson.getTime());
        assertEquals("0", settingJson.getTimezone());
    }

    @Test
    public void testSettersAndGetters() {
        SettingJson settingJson = new SettingJson();

        // Устанавливаем новые значения
        settingJson.setTemperature("Выкл");
        settingJson.setPressure("Выкл");
        settingJson.setHumidity("Выкл");
        settingJson.setWeather("Выкл");
        settingJson.setWind("Выкл");
        settingJson.setCity("Москва");
        settingJson.setNotifications("Вкл");
        settingJson.setTime("12:00");
        settingJson.setTimezone("3");

        // Проверяем, что новые значения получены корректно
        assertEquals("Выкл", settingJson.getTemperature());
        assertEquals("Выкл", settingJson.getPressure());
        assertEquals("Выкл", settingJson.getHumidity());
        assertEquals("Выкл", settingJson.getWeather());
        assertEquals("Выкл", settingJson.getWind());
        assertEquals("Москва", settingJson.getCity());
        assertEquals("Вкл", settingJson.getNotifications());
        assertEquals("12:00", settingJson.getTime());
        assertEquals("3", settingJson.getTimezone());
    }

    @Test
    public void testFromJson() throws Exception {
        String json = "{\"temperature\":\"Выкл\",\"pressure\":\"Выкл\",\"humidity\":\"Выкл\",\"weather\":\"Выкл\",\"wind\":\"Выкл\",\"city\":\"Москва\",\"notifications\":\"Вкл\",\"time\":\"12:00\",\"timezone\":\"3\"}";

        // Преобразуем JSON в объект SettingJson
        SettingJson settingJson = SettingJson.fromJson(json);

        // Проверяем, что объект получен корректно
        assertNotNull(settingJson);
        assertEquals("Выкл", settingJson.getTemperature());
        assertEquals("Выкл", settingJson.getPressure());
        assertEquals("Выкл", settingJson.getHumidity());
        assertEquals("Выкл", settingJson.getWeather());
        assertEquals("Выкл", settingJson.getWind());
        assertEquals("Москва", settingJson.getCity());
        assertEquals("Вкл", settingJson.getNotifications());
        assertEquals("12:00", settingJson.getTime());
        assertEquals("3", settingJson.getTimezone());
    }

    @Test
    public void testToJson() throws Exception {
        SettingJson settingJson = new SettingJson();
        settingJson.setCity("Санкт-Петербург");
        settingJson.setNotifications("Вкл");

        // Преобразуем объект SettingJson в JSON
        String json = settingJson.toJson();

        // Проверяем, что JSON получен корректно
        assertNotNull(json);
        assertEquals("{\"temperature\":\"Вкл\",\"pressure\":\"Вкл\",\"humidity\":\"Вкл\",\"weather\":\"Вкл\",\"wind\":\"Вкл\",\"city\":\"Санкт-Петербург\",\"notifications\":\"Вкл\",\"time\":\"Не задано\",\"timezone\":\"0\"}", json);
    }
}
