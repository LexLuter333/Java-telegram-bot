package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

import static org.kirillandrey.alerting.AlertUtil.signalUserListChanged;

public class DefaultSettings implements Dialog {
    private String m_ask = "Вы действительно хотите сбросить настройки по умолчанию? (Введите: \"Подтвердить\" / или вернитесь \"Назад\")";
    List<String> keyboard = new ArrayList<>();
    private String key = "сбросить по умолчанию";
    @Override
    public Entry_Ask ask(Long chatid) {
        Entry_Ask entryAsk = new Entry_Ask();
        entryAsk.setM_ask(m_ask);
        entryAsk.setButton(keyboard);
        return entryAsk;
    }

    @Override
    public String answer(String message, Long chatid) {
        if (message.equalsIgnoreCase("подтвердить")) {
            DateBaseHandler dbhendler = new DateBaseHandler();
            dbhendler.setState(chatid, "3");

            if (dbhendler.setSettings(chatid, new SettingJson())) {
                dbhendler.removeUserFromNotificationTable(chatid);
                signalUserListChanged();
                return "Настройки успешно " +
                        "сброшены по умолчанию";
            }
            return "При сбросе настроек произошла ошибка. Попробуйте позже.";
        } return "";
    }


    @Override
    public String getKey() {
        return key;
    }
}
