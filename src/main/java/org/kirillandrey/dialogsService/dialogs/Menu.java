package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.Dialog;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Dialog {
    private String m_ask = "Вы находитесь в меню";
    List<String> keyboard = new ArrayList<>();
    private String key = "меню";
    public Menu(){
        keyboard.add("Узнать погоду");
        keyboard.add("Узнать погоду по геолокации");
        keyboard.add("Настройки");
    }
    @Override
    public String ask(Long chatid, List<String> button) {
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
