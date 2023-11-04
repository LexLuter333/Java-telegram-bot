package org.kirillandrey.commandService.controller;

import org.kirillandrey.commandService.commands.*;

import java.sql.SQLException;
import java.util.HashMap;

public class CommandHandler{
    private HashMap<String, Command> commandMap = new HashMap<>();

    public CommandHandler() {
        commandMap.put("/start", new Start());
        commandMap.put("/authors", new Authors());
        commandMap.put("/about", new About());
        commandMap.put("/weather", new Weather());
        commandMap.put("/help", new Help());
    }

    public String handleCommand(Long chatid, String command, String[] args) throws SQLException {
        Command cmd = commandMap.get(command);
        if (cmd != null) {
           return cmd.execute(chatid, args);
        } else {
            return "Извините, я не понимаю вашей команды";
        }
    }

    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
