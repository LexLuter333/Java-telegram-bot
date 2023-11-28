package org.kirillandrey.dialogsService.dialogs.Settings;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;

import java.util.ArrayList;
import java.util.List;

public class Settings implements Dialog {
    private String m_ask;
    List<String> keyboard = new ArrayList<>();
    private String key = "настройки";
    public Settings(){
        keyboard.add("Меню");
        keyboard.add("Сбросить по умолчанию");
    }
    @Override
    public String ask(Long chatid, List<String> button) {
        button.clear();
        button.addAll(keyboard);
        SettingJson settingJson = new DateBaseHandler().getSettings(chatid);
        m_ask = "Найстройки вывода информации:\n" +
                "   1) Температура [" + settingJson.getTemperature() + "]\n" +
                "   2) Давление [" + settingJson.getPressure() + "]\n" +
                "   3) Влажность [" + settingJson.getHumidity() + "]\n" +
                "   4) Погода [" + settingJson.getWeather() + "]\n" +
                "   5) Ветер [" + settingJson.getWind() + "]\n" +
                "   6) Город по умолчанию [" + settingJson.getCity() + "]\n" +
                "   7) Уведомления [" + settingJson.getNotifications() + "]\n" +
                "   8) Время ежедневного уведомления [" + settingJson.getTime() + "]\n" +
                "   9) Ваш часовой пояс UTC [" + settingJson.getTimezone() + "]\n" +
                "Выберите пункт (цифру) настроек, чтобы изменить его.";
        return m_ask;
    }

    @Override
    public String answer(String message, Long chatid) {
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
