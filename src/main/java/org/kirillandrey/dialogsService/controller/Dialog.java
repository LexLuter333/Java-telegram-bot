package org.kirillandrey.dialogsService.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

public interface Dialog {
    public String ask(List<String> button);
    public String answer(String message, Long chatid);
    public String getKey();
}
