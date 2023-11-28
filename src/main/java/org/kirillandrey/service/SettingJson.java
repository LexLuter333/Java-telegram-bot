package org.kirillandrey.service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Класс {@code SettingJson} работает с настройками вывода информации о погоде.
 * Геттеры и сеттеры реализуют стандартный подход.
 * Поля:
 * 1) Температура
 * 2) Давление
 * 3) Влажность
 * 4) Общая погода
 * 5) Ветер
 * 6) Город по умолчанию
 * 7) Уведомления
 * 8) Время отправки уведомлений
 * 9) Часовой пояс UTC (относительно Гринвича)
 */

public class SettingJson {
    private String temperature;
    private String pressure;
    private String humidity;
    private String weather;
    private String wind;
    private String city;
    private String notifications;
    private String time;
    private String timezone;
    public SettingJson() {
        this.temperature = "Вкл";
        this.pressure = "Вкл";
        this.humidity = "Вкл";
        this.weather = "Вкл";
        this.wind = "Вкл";
        this.city = "Не задан";
        this.notifications = "Выкл";
        this.time = "Не задано";
        this.timezone = "Не задано";
    }
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Метод fromJson преобразования Json объекта в текущий класс
     * @param json
     * @return объект этого класса
     * @throws Exception
     */
    public static SettingJson fromJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, SettingJson.class);
    }

    /**
     * Метод toJson для превращения объекта класса в Json
     * @return json в формате Srting
     * @throws Exception
     */
    public String toJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}