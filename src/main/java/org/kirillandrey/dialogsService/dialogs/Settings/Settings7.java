package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

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
                settingJson.setNotifications("Вкл");
                if (!settingJson.getTime().equals("Не задано")) {
                    String[] time = settingJson.getTime().split(":");
                    int newhour = Math.abs((Integer.parseInt(settingJson.getTimezone()) - 5) + Integer.parseInt(time[0])) % 24;
                    time[0] = String.valueOf(newhour);
                    dateBaseHandler.addUserInNotificationTable(chatid, time[0] + ":" + time[1]);
                }
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы включили уведомление";
            } else if (message.equalsIgnoreCase("выключить") && settingJson.getNotifications().equals("Вкл")) {
                settingJson.setNotifications("Выкл");
                        dateBaseHandler.removeUserFromNotificationTable(chatid);
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
