package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

public class Settings1 implements Dialog {
    private String m_ask = "Выберите \"Включить\" / \"Выключить\" вывод температуры:";
    List<String> keyboard = new ArrayList<>();
    private String key = "1";
    public Settings1(){
        keyboard.add("Включить");
        keyboard.add("Выключить");
    }
    @Override
    public Entry_Ask ask(Long chatid) {
        Entry_Ask entryAsk = new Entry_Ask();
        entryAsk.setM_ask(m_ask);
        entryAsk.setButton(keyboard);
        return entryAsk;
    }

    @Override
    public String answer(String message, Long chatid) {
        SettingJson settingJson = new DateBaseHandler().getSettings(chatid);
        DateBaseHandler dateBaseHandler = new DateBaseHandler();
        if (message.equalsIgnoreCase("выключить") || message.equalsIgnoreCase("включить")) {
            dateBaseHandler.setState(chatid, "3");
            if (message.equalsIgnoreCase("включить") && settingJson.getTemperature().equals("Выкл")) {
                settingJson.setTemperature("Вкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы включили вывод температуры";
            } else if (message.equalsIgnoreCase("выключить") && settingJson.getTemperature().equals("Вкл")) {
                settingJson.setTemperature("Выкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы выключили вывод температуры";
            }
            return "У вас уже стоят такие настройки температуры";
        }
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
