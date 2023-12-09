package org.kirillandrey.WeatherBot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.cdimascio.dotenv.Dotenv;
import org.kirillandrey.JSON.*;
import org.kirillandrey.service.SettingJson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * Класс, отвечающий за получение и обработку погодных данных из API OpenWeather
 */
public class WeatherParse {

    private static String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private static String API_CALL_COORDINATES = "https://api.openweathermap.org/data/2.5/forecast?";
    private static Dotenv dotenv = Dotenv.load();
    private static String apikey = dotenv.get("apiWeatherKey");

    private static String API_KEY_TEMPLATE = "&units=metric&APPID=" + apikey;
    private static String USER_AGENT = "Mozilla/5.0";
    private static DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd MMMM", new Locale("ru", "RU"));

    /**
     * Получает прогноз погоды для указанного города и настроек.
     *
     * @param city     город
     * @param settings настройки вывода информации о погоде
     * @return строка с отформатированным прогнозом погоды
     */
    public static String getReadyForecast(String city, SettingJson settings) {
        String result;

        try {
            String jsonRawData = downloadJsonRawData(city);
            String parsedData = parsePojo(jsonRawData, city, settings);
            result = parsedData;
        } catch (IllegalArgumentException e) {
            return String.format("Не можем найти \"%s\" город. Попробуйе ещё, например: \"Moscow\" or \"Ekateringurg\"", city);
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка подклчения к сервису, попробуйте позже";
        }
        return result;
    }
    public static String getReadyForecast(String latitude, String longitude, SettingJson settings){
        String result;

        try {
            String jsonRawData = downloadJsonRawData(latitude, longitude);
            String parsedData = parsePojo(jsonRawData, "Месторасположение(" + latitude + "; " + longitude + ")", settings);
            result = parsedData;
        } catch (IllegalArgumentException e) {
            return String.format("Не можем найти это место на карте.");
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка подклчения к сервису, попробуйте позже";
        }
        return result;
    }
    public static String downloadJsonRawData(String latitude, String longitude) throws Exception {
        String urlString = API_CALL_COORDINATES + "lat=" + latitude + "&lon=" + longitude + API_KEY_TEMPLATE;
        System.out.println(urlString);
        URL urlObject = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new IllegalAccessException();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
    /**
     * Загружает сырые данные JSON о погоде.
     *
     * @param city город
     * @return строка с сырыми данными JSON
     * @throws Exception если произошла ошибка при загрузке данных
     */
    public static String downloadJsonRawData(String city) throws Exception {
        String urlString = API_CALL_TEMPLATE + city + API_KEY_TEMPLATE;
        URL urlObject = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        if (responseCode == 404) {
            throw new IllegalAccessException();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    /**
     * Преобразует сырые данные JSON в отформатированный прогноз погоды.
     *
     * @param json сырые данные JSON
     * @param city        город
     * @param settings    настройки вывода информации о погоде
     * @return строка с отформатированным прогнозом погоды
     * @throws Exception если произошла ошибка при парсинге данных
     */
    protected static String parsePojo(String json, String city, SettingJson settings) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Example example = objectMapper.readValue(json, Example.class);
        List<org.kirillandrey.JSON.List> allLists = example.getList();

        ZoneOffset zoneOffset = ZoneOffset.ofHours(Integer.parseInt(settings.getTimezone()));

        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneOffset);

        final StringBuffer sb = new StringBuffer();
        sb.append(city).append(":\n");

        int count = 0;
        for (org.kirillandrey.JSON.List list : allLists) {
            LocalDateTime forecastDateTime = LocalDateTime.parse(list.getDtTxt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ZonedDateTime forecastZonedDateTime = forecastDateTime.atZone(zoneOffset);

            if (forecastZonedDateTime.isAfter(currentDateTime)) {
                String formattedDateTime = forecastZonedDateTime.format(OUTPUT_DATE_TIME_FORMAT);
                sb.append("⏰ | " + formattedDateTime + " (" + forecastDateTime.getHour() + ":00)" + "\n");
                sb.append(formatForecastData(list, settings) + "\n");
                count++;

                if (count == 8) {
                    break; // Прерываем цикл, когда достигнут лимит в 8 записей
                }
            }
        }
        return sb.toString();
    }

    /**
     * Форматирует данные прогноза погоды.
     *
     * @param list     объект с данными о погоде
     * @param settings настройки вывода информации о погоде
     * @return отформатированные данные прогноза погоды
     * @throws Exception если произошла ошибка при форматировании данных
     */
    protected static String formatForecastData(org.kirillandrey.JSON.List list , SettingJson settings ) throws Exception {
        Main main = list.getMain();
        StringBuilder sb = new StringBuilder();
        if (settings.getTemperature().equals("Вкл")) {
            double temp = main.getTemp();
            String formattedTemperature;
            long roundedTemperature = Math.round(temp);
            if (roundedTemperature > 0) {
                formattedTemperature = "+" + Math.round(temp);
            } else {
                formattedTemperature = String.valueOf(Math.round(temp));
            }
            sb.append(String.format("\uD83C\uDF21\uFE0F | Температура: %s°C%s", formattedTemperature, System.lineSeparator()));
        }
        if (settings.getPressure().equals("Вкл")){
            long formattedPressure = main.getPressure();
            sb.append(String.format("⏲\uFE0F | Давление: %s hPa%s", formattedPressure , System.lineSeparator()));
        }
        if (settings.getHumidity().equals("Вкл")){
            long formattedHumidity = main.getHumidity();
            sb.append(String.format("\uD83C\uDF00 | Влажность: %s%%%s", formattedHumidity, System.lineSeparator()));
        }
        if (settings.getWeather().equals("Вкл")) {
            sb.append("⛰\uFE0F | Погода: " );
            for (Weather weather : list.getWeather()) {
                sb.append(weather.getlocate(weather.getMain()) + "  ");
            }
            sb.append(System.lineSeparator());
        }
        if (settings.getWind().equals("Вкл")) {
            Wind wind = list.getWind();
            sb.append(String.format("\uD83D\uDCA8 | Ветер: %s м/с (%s)%s", wind.getSpeed(), wind.getDirection(wind.getSpeed()), System.lineSeparator()));
        }

        return sb.toString();
    }

    /**
     * Проверяет, что указанный город существует в сервисе погоды.
     *
     * @param city город
     * @return true, если город существует, в противном случае - false
     */
    public boolean checkCity(String city) {
        try {
            String jsonRawData = downloadJsonRawData(city);
            ObjectMapper objectMapper = new ObjectMapper();
            Example example = objectMapper.readValue(jsonRawData, Example.class);

            // Проверяем, что в ответе есть данные о погоде для указанного города
            return example != null && example.getList() != null && !example.getList().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Обрабатывает html запрос и возвращает все записи погоды на 1 день.
     *
     * @param settingJson настройки
     * @return List<org.kirillandrey.JSON.List> в нем не более 8 записей на ближайшие 24 часа
     */
    public static List<org.kirillandrey.JSON.List> getListForAlert(SettingJson settingJson) throws Exception {
        String city = settingJson.getCity();
        String timezone = settingJson.getTimezone();
        String jsonRawData;
        try {
            jsonRawData = downloadJsonRawData(city);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Example example = objectMapper.readValue(jsonRawData, Example.class);
        List<org.kirillandrey.JSON.List> allLists = example.getList();

        List<org.kirillandrey.JSON.List> result = new ArrayList<>();

        // Получаем текущее время в указанном часовом поясе
        ZoneOffset zoneOffset = ZoneOffset.ofHours(Integer.parseInt(timezone));

        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneOffset);

        int i = 0;
        for (org.kirillandrey.JSON.List list : allLists) {
            // Преобразуем временную метку из JSON в ZonedDateTime
            LocalDateTime forecastDateTime = LocalDateTime.parse(list.getDtTxt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ZonedDateTime forecastZonedDateTime = forecastDateTime.atZone(zoneOffset);

            // Сравниваем с текущим временем и добавляем в результат, если удовлетворяет условиям
            if (forecastZonedDateTime.isAfter(currentDateTime) && i < 8) {
                result.add(list);
                i++;
            }
        }
        return result;
    }
}