package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Settings9 implements Dialog {
    private String m_ask = "Введите ваш часовой пояс относительно Гринвича включительно от -12 до 12:";
    List<String> keyboard = new ArrayList<>();
    private String key = "9";
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
        try {
            int number = Integer.parseInt(message);
            if (number >= -12 && number <= 12){
                try {
                    settingJson.setTimezone(String.valueOf(number));
                    dateBaseHandler.setSettings(chatid, settingJson);
                    dateBaseHandler.setState(chatid, "3");
                    return "Вы успешно изменили ваш часовой пояс";
                } catch (DateTimeParseException e) {
                    return "";
                }
            }
        } catch (NumberFormatException e) {
            return "";
        }
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
