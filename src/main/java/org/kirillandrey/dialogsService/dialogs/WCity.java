package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.WeatherBot.WeatherParse;
import org.kirillandrey.dialogsService.controller.CacheCountDays;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.service.DateBaseHandler;

import java.util.ArrayList;
import java.util.List;

public class WCity implements Dialog {
    private String m_ask = "Введите название города:";

    List<String> keyboard = new ArrayList<>();
    private String key = "узнать погоду по городу";
    public WCity() {
        keyboard.add("Назад");
    }
    @Override
    public Entry_Ask ask(Long chatid) {
        Entry_Ask entryAsk = new Entry_Ask();
        entryAsk.setM_ask(m_ask);
        entryAsk.setButton(keyboard);
        return entryAsk;
    }

    @Override
    public String answer(String message, Long chatid) {
        Integer days = CacheCountDays.getDays(chatid);
        if (days != null) {
            return WeatherParse.getReadyForecast(message, new DateBaseHandler().getSettings(chatid), days);
        }
        return "Время ожидания вышло.";
    }

    @Override
    public String getKey() {
        return key;
    }
}
