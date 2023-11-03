package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.dialogsService.dialogs.*;
import org.kirillandrey.service.DateBaseHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

public class DialogHandler {
    HashMap<Long, String> Cache = new HashMap<>();
    HashMap<String, Dialog> Table = new HashMap<>();

    public DialogHandler(){
        Table.put("1", new Menu());
        Table.put("2", new WCity());
        Table.put("3", new WCityСycle());
    }

    public String handleDialog(String command, Update update, List<String> button) {
        Long chatid = update.getMessage().getChatId();
        String s_dlg = Cache.get(chatid);
        Dialog dlg = Table.get(s_dlg);
        if (dlg == null){
            String state = new DateBaseHandler().getState(chatid);
            dlg = Table.get(state);
            Cache.put(update.getMessage().getChatId(), state);
            return dlg.answer(command, update, button);
        } else {
            return dlg.answer(command, update, button);
        }
    }
}
