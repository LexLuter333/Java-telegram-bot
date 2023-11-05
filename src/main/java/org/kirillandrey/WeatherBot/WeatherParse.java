package org.kirillandrey.WeatherBot;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.cdimascio.dotenv.Dotenv;
import org.kirillandrey.JSON.Example;
import org.kirillandrey.JSON.Main;
import org.kirillandrey.JSON.Weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherParse {

    private final static String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private static String dotenv = "7c62968a0badd83d507275a3f5104444";

    private final static String API_KEY_TEMPLATE = "&units=metric&APPID=" + dotenv;
    private final static String USER_AGENT = "Mozilla/5.0";
    private final static DateTimeFormatter INPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM-dd HH:mm", Locale.US);

    public static String getReadyForecast(String city) {
        String result;

        try {
            String jsonRawData = downloadJsonRawData(city);
            String parsedData = parsePojo(jsonRawData, city);
            result = parsedData;
        } catch (IllegalArgumentException e) {
            return String.format("Не можем найти \"%s\" город. Попробуйе ещё, например: \"Moscow\" or \"Ekateringurg\"", city);
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка подклчения к сервису, попробуйте позже";
        }
        return result;
    }

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

    private static String parsePojo(String Json, String city) throws Exception {
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

            if (forecastDateTime.getYear() == currentDateTime.getYear() &&
                    forecastDateTime.getMonth() == currentDateTime.getMonth() &&
                    forecastDateTime.getDayOfMonth() == currentDateTime.getDayOfMonth()) {

                Main main = list.getMain();
                List<Weather> weatherList = list.getWeather();

                for (Weather weather : weatherList) {
                    sb.append(formatForecastData(list.getDtTxt(), weather.getMain(), main.getTemp()));
                }
            }
        }

        return sb.toString();
    }

    private static String formatForecastData(String dateTime, String description, double temperature) throws Exception {
        LocalDateTime forecastDateTime = LocalDateTime.parse(dateTime.replaceAll("\"", ""), INPUT_DATE_TIME_FORMAT);
        String formattedDateTime = forecastDateTime.format(OUTPUT_DATE_TIME_FORMAT);

        String formattedTemperature;
        long roundedTemperature = Math.round(temperature);
        if (roundedTemperature > 0) {
            formattedTemperature = "+" + String.valueOf(Math.round(temperature));
        } else {
            formattedTemperature = String.valueOf(Math.round(temperature));
        }

        String formattedDescription = description.replaceAll("\"", "");

        String weatherIconCode = WeatherUtils.weatherIconsCodes.get(formattedDescription);

        return String.format("%s   %s %s %s%s", formattedDateTime, formattedTemperature, formattedDescription, weatherIconCode, System.lineSeparator());
    }
}