package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

public class Settings2 implements Dialog {
    private String m_ask = "Выберите \"Включить\" / \"Выключить\" вывод давления:";
    List<String> keyboard = new ArrayList<>();
    private String key = "2";
    public Settings2(){
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
            if (message.equalsIgnoreCase("включить") && settingJson.getPressure().equals("Выкл")) {
                settingJson.setPressure("Вкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы включили вывод давления";
            } else if (message.equalsIgnoreCase("выключить") && settingJson.getPressure().equals("Вкл")) {
                settingJson.setPressure("Выкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы выключили вывод давления";
            }
            return "У вас уже стоят такие настройки давления";
        }
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
