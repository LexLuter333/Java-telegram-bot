package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class WCityСyrcle implements Dialog {
    private String m_ask = "Чтобы посмотреть погоду ещё раз, нажмите \"Узнать погоду\" или вернитесь в \"Меню\". ";
    List<String> keyboard = new ArrayList<>();
    private String key;
    public WCityСyrcle(){
        keyboard.add("Узнать погоду");
        keyboard.add("Меню");
    }
    @Override
    public String ask(List<String> button) {
        button = keyboard;
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
