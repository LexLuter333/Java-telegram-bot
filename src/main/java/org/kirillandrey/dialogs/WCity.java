package org.kirillandrey.dialogs;

import org.kirillandrey.WeatherBot.WeatherJsonParser;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class WCity implements Dialog {
    String s_Ask = "Введите название города:";
    String s_Answer = "";
    @Override
    public String Ask() {
        return s_Ask;
    }

    @Override
    public String Answer(String message, Update update, HashMap<Long, String> cache) {
        s_Answer = "";
        new DateBaseHandler().setState(update.getMessage().getChatId(),"узнать погоду ещё");
        cache.put(update.getMessage().getChatId(), "узнать погоду ещё");
        return s_Answer = WeatherJsonParser.getReadyForecast(message) + "\n" + new WCity2().s_Ask;
    }
}
