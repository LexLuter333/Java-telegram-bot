package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    public String ask() {
        return s_Ask;
    }

    @Override
    public String answer(String message, Update update, List<String> button) {
        return s_Answer;
    }
}
