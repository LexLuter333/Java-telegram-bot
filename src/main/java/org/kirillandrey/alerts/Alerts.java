package org.kirillandrey.alerts;

import org.kirillandrey.WeatherBot.WeatherParse;
import org.kirillandrey.config.BotConfig;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;
import org.kirillandrey.service.TelegramBot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Alerts implements Runnable {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final DateBaseHandler dbHandler = new DateBaseHandler(); // Создаем объект для работы с базой данных

    public void startAlerts() {
        scheduler.scheduleAtFixedRate(this, 0, 1, TimeUnit.DAYS); // Запускаем задачу каждый день
    }

    @Override
    public void run() {
        TelegramBot bot = new TelegramBot(new BotConfig());
        while (true) {
            // Получаем текущее время
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm");
            String nowDate = formatForDateNow.format(dateNow);

            // Получаем список данных для оповещения из базы данных
            HashMap<String, String> dataTable = dbHandler.getTimesForAlerts();

            for (Map.Entry<String, String> entry : dataTable.entrySet()) {
                // Сравниваем текущее время с временем оповещения
                if (entry.getValue().equals(nowDate)) {

                    Long chatid = Long.valueOf(entry.getKey());
                    SettingJson settingJson = dbHandler.getSettings(chatid);
                    String city = settingJson.getCity();

                    String result = WeatherParse.getReadyForecast(city, settingJson);
                    bot.sendMessage(chatid, result, new ArrayList<>());
                }
            }
        }

    }

    public void stopAlerts() {
        scheduler.shutdown();
    }
}
