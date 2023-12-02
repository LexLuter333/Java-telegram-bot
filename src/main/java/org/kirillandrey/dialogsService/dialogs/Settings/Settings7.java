package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.alerting.Alert;
import org.kirillandrey.alerting.EntryUser;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.kirillandrey.alerting.AlertUtil.signalUserListChanged;

public class Settings7 implements Dialog {
    private String m_ask = "Выберите \"Включить\" / \"Выключить\" уведомления:";
    List<String> keyboard = new ArrayList<>();
    private String key = "7";
    public Settings7(){
        keyboard.add("Включить");
        keyboard.add("Выключить");
    }
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
        if (message.equalsIgnoreCase("выключить") || message.equalsIgnoreCase("включить")) {
            dateBaseHandler.setState(chatid, "3");
            if (message.equalsIgnoreCase("включить") && settingJson.getNotifications().equals("Выкл")) {
                if (settingJson.getCity().equals("Не задан"))
                {
                    return "Ошибка, введите город по умолчанию.";
                } if (settingJson.getTime().equals("Не задано")){
                    return "Ошибка, введите время ежедневного уведомления.";
                }
                settingJson.setNotifications("Вкл");
                if (!settingJson.getTime().equals("Не задан")) {
                    String[] time = settingJson.getTime().split(":");
                    int userTimezone = Integer.parseInt(settingJson.getTimezone());
                    int newhour = Math.floorMod((5 - userTimezone) + Integer.parseInt(time[0]), 24);
                    time[0] = String.format("%02d", newhour);
                    dateBaseHandler.addUserInNotificationTable(chatid, time[0] + ":" + time[1]);
                    signalUserListChanged();
                }
                dateBaseHandler.setSettings(chatid, settingJson);

                return "Вы включили уведомление";
            } else if (message.equalsIgnoreCase("выключить") && settingJson.getNotifications().equals("Вкл")) {
                settingJson.setNotifications("Выкл");
                dateBaseHandler.removeUserFromNotificationTable(chatid);
                signalUserListChanged();
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы выключили уведомление";
            }
            return "У вас уже стоят такие настройки уведомления";
        }
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
