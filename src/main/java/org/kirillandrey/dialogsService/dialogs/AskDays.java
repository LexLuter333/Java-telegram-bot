package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.CacheCountDays;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;

import java.util.ArrayList;
import java.util.List;

public class AskDays implements Dialog {
    private String m_ask = "Выберите или впишите количество дней для просомотра проноза погоды (от 1 до 5 включительно):";

    List<String> keyboard = new ArrayList<>();
    private String key = "узнать погоду";

    public AskDays() {
        keyboard.add("1");
        keyboard.add("3");
        keyboard.add("5");
        keyboard.add("Назад");
    }

    @Override
    public String ask(Long chatid, List<String> button) {
        button.clear();
        button.addAll(keyboard);
        return m_ask;
    }

    @Override
    public String answer(String message, Long chatid) {
        try {
            Integer days = Integer.parseInt(message);
            if (days >= 1 && days <= 5) {
                CacheCountDays.addDays(chatid, days);
                return "";
            }
        } catch (Exception e) {
            new DateBaseHandler().setState(chatid, "15");
            return "Ошибка! Введите натуральное число.";
        }
        new DateBaseHandler().setState(chatid, "15");
        return "Ошибка! Введите число из диапазона.";
    }

    @Override
    public String getKey() {
        return key;
    }
}
