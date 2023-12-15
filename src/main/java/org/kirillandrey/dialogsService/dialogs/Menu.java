package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Dialog {
    private String m_ask = "Вы находитесь в меню";
    List<String> keyboard = new ArrayList<>();
    private String key = "меню";
    public Menu(){
        keyboard.add("Узнать погоду");
        keyboard.add("Настройки");
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
