package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ErrorInput implements Dialog {
    private String m_ask = "Ошибка ввода, вернитесь в \"Меню\"";

    List<String> keyboard = new ArrayList<>();
    private String key;
    public ErrorInput(){
        keyboard.add("Меню");
    }
    @Override
    public String ask(List<String> button) {
        button.clear();
        button.addAll(keyboard);
        return m_ask;
    }

    @Override
    public String answer(String message, Long chatid) {
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
