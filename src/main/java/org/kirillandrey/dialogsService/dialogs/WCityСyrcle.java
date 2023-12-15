package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.dialogsService.controller.Dialog;
import org.kirillandrey.dialogsService.controller.Entry_Ask;

import java.util.ArrayList;
import java.util.List;

public class WCityСyrcle implements Dialog {
    private String m_ask = "Чтобы посмотреть погоду ещё раз, нажмите \"Узнать погоду\", или вернитесь в \"Меню\".";
    List<String> keyboard = new ArrayList<>();
    private String key;
    public WCityСyrcle(){
        keyboard.add("Узнать погоду");
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
