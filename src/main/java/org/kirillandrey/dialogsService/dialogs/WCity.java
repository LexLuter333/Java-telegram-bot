package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.weatherBot.WeatherJsonParser;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WCity implements Dialog {
    private String m_ask = "Введите название города:";

    List<String> keyboard = new ArrayList<>();
    private String key = "узнать погоду";
    @Override
    public String ask(List<String> button) {
        return m_ask;
    }

    @Override
    public String answer(String message, Long chatid) {
        return WeatherJsonParser.getReadyForecast(message);
    }

    @Override
    public String getKey() {
        return key;
    }
}
