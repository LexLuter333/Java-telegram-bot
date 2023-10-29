package org.kirillandrey.dialogs;

import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.Dialog;
import org.kirillandrey.service.DialogHandler;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Menu implements Dialog {
    String s_Ask = "Вы находитесь в меню";
    String s_Answer = "";
    List<KeyboardRow> keyboard = new ArrayList<>();
    public Menu(){
        KeyboardRow row = new KeyboardRow();
        row.add("Узнать погоду");
        row.add("Настройки");
        keyboard.add(row);
    }
    @Override
    public String Ask() {
        return s_Ask;
    }

    @Override
    public String Answer(String message, Update update, HashMap<Long, String> cache) {
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
        return s_Answer;
    }
}
