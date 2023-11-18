package org.kirillandrey.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SettingJson {
    private String temperature;
    private String pressure;
    private String humidity;
    private String weather;
    private String wind;
    private String city;
    private String notifications;
    public SettingJson() {
        this.temperature = "Вкл";
        this.pressure = "Вкл";
        this.humidity = "Вкл";
        this.weather = "Вкл";
        this.wind = "Вкл";
        this.city = "Не задан";
        this.notifications = "Выкл";
    }

    // Геттеры и сеттеры
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

    public static SettingJson fromJson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, SettingJson.class);
    }
    public String toJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}