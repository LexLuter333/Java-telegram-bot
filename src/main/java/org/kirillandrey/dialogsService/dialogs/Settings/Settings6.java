package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.WeatherBot.WeatherParse;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

public class Settings6 implements Dialog {
    private String m_ask = "Введите новый город по умолчанию:";
    List<String> keyboard = new ArrayList<>();
    private String key = "6";
    public Settings6(){
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
        dateBaseHandler.setState(chatid, "3");
        WeatherParse weatherParse = new WeatherParse();
        if (weatherParse.checkCity(message)){
            settingJson.setCity(message);
            dateBaseHandler.setSettings(chatid, settingJson);
            return "Вы успешно устновили город \"" + message + "\" по умолчанию";
        } else {
            return "Такого города не существует";
        }
    }

    @Override
    public String getKey() {
        return key;
    }
}
