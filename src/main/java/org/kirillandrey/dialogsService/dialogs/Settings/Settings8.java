package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Settings8 implements Dialog {
    private String m_ask = "Введите вермя отправики уведомления (Пример:  16:21):";
    List<String> keyboard = new ArrayList<>();
    private String key = "8";
    @Override
    public String ask(Long chatid, List<String> button) {
        button.clear();
        button.addAll(keyboard);
        return m_ask;
    }

    @Override
    public String answer(String message, Long chatid) {
        SettingJson settingJson = new DateBaseHandler().getSettings(chatid);
        DateBaseHandler dateBaseHandler = new DateBaseHandler();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime parsedTime = LocalTime.parse(message, formatter);
            settingJson.setTime(parsedTime.toString());
            dateBaseHandler.setSettings(chatid, settingJson);
            dateBaseHandler.addUserInNotificationTable(chatid, parsedTime.toString());
            dateBaseHandler.setState(chatid, "3");
            return "Вы успешно изменили время отправки уведомлений";
        } catch (DateTimeParseException e) {
            return "";
        }
    }

    @Override
    public String getKey() {
        return key;
    }
}
