package org.kirillandrey.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String execute(Update update);
}