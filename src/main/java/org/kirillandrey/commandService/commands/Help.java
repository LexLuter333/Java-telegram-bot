package org.kirillandrey.commandService.commands;

import org.kirillandrey.commandService.controller.Command;
import org.kirillandrey.commandService.controller.CommandHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class Help implements Command {
    private String resault;
    private String info = "<комманда> узнать что выполняет данная команда.";
    @Override
    public String execute(Update update,  String[] args) {
        resault = "";
        HashMap<String, Command> commandMap = new CommandHandler().getCommandMap();
        if (args.length == 1) {
            for (HashMap.Entry<String, Command> temp : commandMap.entrySet()) {
                String key = temp.getKey();
                Command value = temp.getValue();
                resault = resault + key + " - " + value.getInfo() + "\n";
            }
        } else if (args.length == 2 && commandMap.containsValue(args[1])) {
            resault = "/" + args[1] + " - " + commandMap.get("/" + args[1]).getInfo();
        } else {
            resault = "Извините, я не нашёл команду, которую вы ищите";
        }
        return resault;
    }
    public String getInfo(){
        return info;
    }
}
