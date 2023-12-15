package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;

import java.util.ArrayList;
import java.util.List;

public class ChooseMethod implements Dialog {
    private String m_ask = "Выберите метод получения локации для прогноза погоды (По городу или по геолокации)";

    List<String> keyboard = new ArrayList<>();
    private String key;

    public ChooseMethod() {
        keyboard.add("Узнать погоду по городу");
        keyboard.add("Узнать погоду по геолокации");
        keyboard.add("Меню");
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
        return "";
    }

    @Override
    public String getKey() {
        return key;
    }
}
