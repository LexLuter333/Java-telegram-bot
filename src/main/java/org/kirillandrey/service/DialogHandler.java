package org.kirillandrey.service;

import org.kirillandrey.dialogs.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

public class DialogHandler {
    HashMap<Long, String> Cache = new HashMap<>();
    HashMap<String, Dialog> Table = new HashMap<>();

    public DialogHandler(){
        Table.put("меню", new Menu());
        Table.put("узнать погоду", new WCity());
        Table.put("узнать погоду ещё", new WCity2());
    }

    public String handleDialog(String command, Update update, List<String> button) {
        Long chatid = update.getMessage().getChatId();
        String s_dlg = Cache.get(chatid);
        Dialog dlg = Table.get(s_dlg);
        if (dlg == null){
            String state = new DateBaseHandler().getState(chatid);
            dlg = Table.get(state);
            Cache.put(update.getMessage().getChatId(), state);
            return dlg.Answer(command, update, Cache, button);
        } else {
            return dlg.Answer(command, update, Cache, button);
        }
    }
}
