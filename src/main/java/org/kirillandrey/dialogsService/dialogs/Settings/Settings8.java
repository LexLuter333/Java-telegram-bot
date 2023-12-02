package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.kirillandrey.alerting.AlertUtil.signalUserListChanged;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

        try {
            LocalTime parsedTime = LocalTime.parse(message, formatter);
            String formattedTime = parsedTime.format(formatter);

            settingJson.setTime(formattedTime);
            dateBaseHandler.setSettings(chatid, settingJson);

            if (settingJson.getNotifications().equals("Вкл")) {
                String[] time = formattedTime.split(":");
                int userTimezone = Integer.parseInt(settingJson.getTimezone());
                int newhour = Math.floorMod((5 - userTimezone) + Integer.parseInt(time[0]), 24);
                time[0] = String.format("%02d", newhour);
                dateBaseHandler.addUserInNotificationTable(chatid, time[0] + ":" + time[1]);
                signalUserListChanged();
            }

            dateBaseHandler.setState(chatid, "3");
            return "Вы успешно изменили время отправки уведомлений";
        } catch (DateTimeParseException e) {
            return "Неверный формат времени. Пожалуйста, используйте формат ЧЧ:мм, например, 09:00.";
        }
    }
    @Override
    public String getKey() {
        return key;
    }
}
