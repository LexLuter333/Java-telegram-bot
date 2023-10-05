package org.kirillandrey.service;

import org.kirillandrey.commands.commandStart;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler{
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        commandMap.put("/start", new commandStart());

    }

    public String handleCommand(String command, Update update) {
        Command cmd = commandMap.get(command);
        if (cmd != null) {
           return cmd.execute(update);
        } else {
            return "Sorry, i am don't undestand your command";
        }
    }
}
