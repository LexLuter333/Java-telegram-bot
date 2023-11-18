package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

public class DefaultSettings implements Dialog {
    private String m_ask = "Вы действительно хотите сбросить настройки по умолчанию? (Введите: \"Подтвердить\" / или вернитесь \"Назад\")";
    List<String> keyboard = new ArrayList<>();
    private String key = "сбросить по умолчанию";
    @Override
    public String ask(Long chatid, List<String> button) {
        button.clear();
        button.addAll(keyboard);
        return m_ask;
    }

    @Override
    public String answer(String message, Long chatid) {
        if (message.equalsIgnoreCase("подтвердить")) {
            new DateBaseHandler().setState(chatid, "0");
            if (new DateBaseHandler().setSettings(chatid, new SettingJson())) {
                return "Настройки успешно " +
                        "сброшены по умолчанию";
            }
            return "При сбросе настроек произошла ошибка. Попробуйте позже.";
        } return "";
    }


    @Override
    public String getKey() {
        return key;
    }
}
