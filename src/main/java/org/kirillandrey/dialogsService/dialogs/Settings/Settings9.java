package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.kirillandrey.alerting.AlertUtil.signalUserListChanged;

public class Settings9 implements Dialog {
    private String m_ask = "Введите ваш часовой пояс относительно Гринвича включительно от -12 до 12:";
    List<String> keyboard = new ArrayList<>();
    private String key = "9";
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
        try {
            int number = Integer.parseInt(message);
            if (number >= -12 && number <= 12){
                try {
                    settingJson.setTimezone(String.valueOf(number));
                    dateBaseHandler.setSettings(chatid, settingJson);
                    if (!settingJson.getTime().equals("Не задано") && settingJson.getNotifications().equals("Вкл")) {
                        String[] time = settingJson.getTime().split(":");
                        int userTimezone = Integer.parseInt(settingJson.getTimezone());
                        int newhour = Math.floorMod((5 - userTimezone) + Integer.parseInt(time[0]), 24);
                        time[0] = String.format("%02d", newhour);
                        dateBaseHandler.addUserInNotificationTable(chatid, time[0] + ":" + time[1]);
                        signalUserListChanged();
                    }
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
