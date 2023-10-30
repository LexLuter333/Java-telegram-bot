package org.kirillandrey.dialogs;

import org.kirillandrey.WeatherBot.WeatherJsonParser;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WCity implements Dialog {
    String s_Ask = "Введите название города:";
    String s_Answer = "";
    List<String> keyboard = new ArrayList<>();
    @Override
    public String Ask() {
        return s_Ask;
    }

    @Override
    public String Answer(String message, Update update, HashMap<Long, String> cache, List<String> button) {
        s_Answer = "";
        new DateBaseHandler().setState(update.getMessage().getChatId(),"узнать погоду ещё");
        cache.put(update.getMessage().getChatId(), "узнать погоду ещё");
        button.clear();
        button.addAll(new WCity2().keyboard);
        return s_Answer = WeatherJsonParser.getReadyForecast(message) + "\n" + new WCity2().s_Ask;
    }
}
