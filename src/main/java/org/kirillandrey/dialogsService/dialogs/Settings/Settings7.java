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
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы включили уведомление";
            } else if (message.equalsIgnoreCase("выключить") && settingJson.getNotifications().equals("Вкл")) {
                settingJson.setNotifications("Выкл");
                settingJson.setTime("Не задано");
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
