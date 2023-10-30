package org.kirillandrey.dialogs;

import org.kirillandrey.WeatherBot.WeatherJsonParser;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class WCity2 implements Dialog {
    String s_Ask = "Попробовать ещё раз? (Да/Нет)";
    String s_Answer = "";
    List<String> keyboard = new ArrayList<>();
    public WCity2(){
        s_Answer = "";
        keyboard.add("Да");
        keyboard.add("Нет");
    }
    @Override
    public String Ask() {
        return s_Ask;
    }

    @Override
    public String Answer(String message, Update update, HashMap<Long, String> cache, List<String> button) {
        s_Answer = "";
        if (message.toLowerCase(Locale.ROOT).equals("да")) {
            new DateBaseHandler().setState(update.getMessage().getChatId(),"узнать погоду");
            cache.put(update.getMessage().getChatId(), "узнать погоду");
            s_Answer = new WCity().s_Ask;
            button.clear();
            button.addAll(new WCity().keyboard);
        } else if (message.toLowerCase(Locale.ROOT).equals("нет")) {
            new DateBaseHandler().setState(update.getMessage().getChatId(),"меню");
            cache.put(update.getMessage().getChatId(), "меню");
            s_Answer = new Menu().s_Ask;
            button.clear();
            button.addAll(new Menu().keyboard);
        } else {
            s_Answer = "Я вас не понял, используйте (Да/Нет)";
        }

        return s_Answer;
    }
}
