package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.Dialog;

import java.util.ArrayList;
import java.util.List;

public class ErrorInput implements Dialog {
    private String m_ask = "Ошибка ввода, вернитесь в \"Меню\"";

    List<String> keyboard = new ArrayList<>();
    private String key;
    public ErrorInput(){
        keyboard.add("Меню");
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
