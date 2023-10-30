package org.kirillandrey.service;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

public interface Dialog {
    String Ask();

    String Answer(String message, Update update, HashMap<Long, String> cache, List<String> button);

}
