package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

public class Settings5 implements Dialog {
    private String m_ask = "Выберите \"Включить\" / \"Выключить\" вывод ветра:";
    List<String> keyboard = new ArrayList<>();
    private String key = "5";
    public Settings5(){
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
            if (message.equalsIgnoreCase("включить") && settingJson.getWind().equals("Выкл")) {
                settingJson.setWind("Вкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы включили вывод ветра";
            } else if (message.equalsIgnoreCase("выключить") && settingJson.getWind().equals("Вкл")) {
                settingJson.setWind("Выкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы выключили вывод ветра";
            }
            return "У вас уже стоят такие настройки ветра";
        }
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
