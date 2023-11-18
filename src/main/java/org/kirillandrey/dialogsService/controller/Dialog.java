package org.kirillandrey.dialogsService.controller;

import java.util.List;

public interface Dialog {
    public String ask(Long chatid, List<String> button);
    public String answer(String message, Long chatid);
    public String getKey();
}
