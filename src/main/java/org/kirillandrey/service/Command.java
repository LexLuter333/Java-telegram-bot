package org.kirillandrey.service;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public interface Command {
    String execute(Update update, HashMap<String, Command> commandMap, String[] args);
    String getInfo();
}