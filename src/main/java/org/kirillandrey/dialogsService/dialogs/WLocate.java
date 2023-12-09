package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.WeatherBot.WeatherParse;
import org.kirillandrey.dialogsService.controller.CacheCountDays;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.ArrayList;
import java.util.List;

public class WLocate implements Dialog {
    private String m_ask = "Нажмите на кнопку \"Отправить геолокацию\" \n(У телеграмма должен быть доступ к вашей геолокации, а так же эта функция пока не доступна в desktop версии телеграма):";
    List<String> keyboard = new ArrayList<>();
    private String key = "узнать погоду по геолокации";
    public WLocate() {
        keyboard.add("Отправить геолокацию");
        keyboard.add("Назад");
    }
    @Override
    public String ask(Long chatid, List<String> button) {
        button.clear();
        button.addAll(keyboard);
        return m_ask;
    }

    @Override
    public String answer(String message, Long chatid) {
        if (!message.isEmpty() && !message.equals("")) {
            String latitude = message.split(" ")[0];
            String longitude = message.split(" ")[1];
            Integer days = CacheCountDays.getDays(chatid);

            if (days != null) {
                return WeatherParse.getReadyForecast(latitude, longitude, new DateBaseHandler().getSettings(chatid), days);
            }
            return "Время ожидания вышло.";

        }
        return "Ошибка получения данных геолокации";
    }

    @Override
    public String getKey() {
        return key;
    }
}
