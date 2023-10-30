package org.kirillandrey.dialogs;

import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Menu implements Dialog {
    String s_Ask = "Вы находитесь в меню";
    String s_Answer = "";
    List<String> keyboard = new ArrayList<>();
    public Menu(){
        keyboard.add("Узнать погоду");
        keyboard.add("Настройки");
    }
    @Override
    public String Ask() {
        return s_Ask;
    }

    @Override
    public String Answer(String message, Update update, HashMap<Long, String> cache, List<String> button) {
        s_Answer = "";
        message = message.toLowerCase(Locale.ROOT);
        if (message.equals("узнать погоду")){
            s_Answer = new WCity().s_Ask;
            new DateBaseHandler().setState(update.getMessage().getChatId(),"узнать погоду");
            cache.put(update.getMessage().getChatId(),"узнать погоду");
            s_Answer = new WCity().s_Ask;
        } else {
            s_Answer = "Ошибка, такого действия не существет.";
        }
        button.clear();
        button.addAll(new WCity().keyboard);
        return s_Answer;
    }
}
