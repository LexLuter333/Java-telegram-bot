package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

public class Settings3 implements Dialog {
    private String m_ask = "Выберите \"Включить\" / \"Выключить\" вывод влажности:";
    List<String> keyboard = new ArrayList<>();
    private String key = "3";
    public Settings3(){
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
            if (message.equalsIgnoreCase("включить") && settingJson.getHumidity().equals("Выкл")) {
                settingJson.setHumidity("Вкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы включили вывод влажности";
            } else if (message.equalsIgnoreCase("выключить") && settingJson.getHumidity().equals("Вкл")) {
                settingJson.setHumidity("Выкл");
                dateBaseHandler.setSettings(chatid, settingJson);
                return "Вы выключили вывод влажности";
            }
            return "У вас уже стоят такие настройки влажности";
        }
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
