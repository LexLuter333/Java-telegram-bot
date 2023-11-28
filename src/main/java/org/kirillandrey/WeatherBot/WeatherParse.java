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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
/**
 * Класс, отвечающий за получение и обработку погодных данных из API OpenWeather
 */
public class WeatherParse {

    private static String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?q=";
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
     * @param Json сырые данные JSON
     * @param city        город
     * @param settings    настройки вывода информации о погоде
     * @return строка с отформатированным прогнозом погоды
     * @throws Exception если произошла ошибка при парсинге данных
     */
    protected static String parsePojo(String Json, String city, SettingJson settings) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        Example example = objectMapper.readValue(Json, Example.class);
        List<org.kirillandrey.JSON.List> lists = example.getList();

        LocalDateTime currentDateTime = LocalDateTime.now();

        final StringBuffer sb = new StringBuffer();
        sb.append(city).append(":\n");
        for (org.kirillandrey.JSON.List list : lists) {
            LocalDateTime forecastDateTime = LocalDateTime.parse(list.getDtTxt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String formattedDateTime = forecastDateTime.format(OUTPUT_DATE_TIME_FORMAT);
            if (forecastDateTime.getYear() == currentDateTime.getYear() &&
                    forecastDateTime.getMonth() == currentDateTime.getMonth() &&
                    forecastDateTime.getDayOfMonth() == currentDateTime.getDayOfMonth()) {
                sb.append("⏰ | "  + formattedDateTime + " (" + forecastDateTime.getHour() + ":00)" + "\n");
                sb.append(formatForecastData(list, settings) + "\n");
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
                formattedTemperature = "+" + String.valueOf(Math.round(temp));
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
            for (Weather weather : list.getWeather()) {
                sb.append(String.format("⛰\uFE0F | Погода: %s%s", weather.getlocate(weather.getMain()), System.lineSeparator()));
            }
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
}