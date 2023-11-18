package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.WeatherBot.WeatherParse;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;

import java.util.ArrayList;
import java.util.List;

public class WCity implements Dialog {
    private String m_ask = "Введите название города:";

    List<String> keyboard = new ArrayList<>();
    private String key = "узнать погоду";
    public WCity() {
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
        return WeatherParse.getReadyForecast(message, new DateBaseHandler().getSettings(chatid));
    }

    @Override
    public String getKey() {
        return key;
    }
}
