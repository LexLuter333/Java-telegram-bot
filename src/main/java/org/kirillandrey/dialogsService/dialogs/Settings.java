package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.Dialog;

import java.util.ArrayList;
import java.util.List;

public class Settings implements Dialog {
    private String m_ask = "Вы находитесь в меню";
    List<String> keyboard = new ArrayList<>();
    private String key = "настройки";
    public Settings(){
        keyboard.add("Изменить");
        keyboard.add("Сбросить по умолчанию");
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
