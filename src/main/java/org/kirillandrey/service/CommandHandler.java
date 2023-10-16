package org.kirillandrey.service;

import org.kirillandrey.commands.*;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class CommandHandler{
    public final HashMap<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        commandMap.put("/start", new Start());
        commandMap.put("/authors", new Authors());
        commandMap.put("/about", new About());

        commandMap.put("/help", new Help());
    }

    public String handleCommand(String command, Update update, String[] args) {
        Command cmd = commandMap.get(command);
        if (cmd != null) {
           return cmd.execute(update, args);
        } else {
            return "Извините, я не понимаю вашей команды";
        }
    }

    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
