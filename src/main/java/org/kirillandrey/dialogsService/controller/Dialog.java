package org.kirillandrey.dialogsService.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

public interface Dialog {
    String ask();
    String answer(String message, Update update, List<String> button);
}
