package org.kirillandrey.dialogs;

import org.kirillandrey.WeatherBot.WeatherJsonParser;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class WCity2 implements Dialog {
    String s_Ask = "Попробовать ещё раз?";
    String s_Answer = "";
    @Override
    public String Ask() {
        return s_Ask;
    }

    @Override
    public String Answer(String message, Update update, HashMap<Long, String> cache) {
        s_Answer = "";
        if (message.equals("Да")) {
            new DateBaseHandler().setState(update.getMessage().getChatId(),"узнать погоду");
            cache.put(update.getMessage().getChatId(), "узнать погоду");
            s_Answer = new WCity().s_Ask;
        } else {
            new DateBaseHandler().setState(update.getMessage().getChatId(),"меню");
            cache.put(update.getMessage().getChatId(), "меню");
            s_Answer = new Menu().s_Ask;
        }
        return s_Answer;
    }
}
