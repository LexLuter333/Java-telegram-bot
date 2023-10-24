package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.kirillandrey.service.CommandHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class Help implements Command {
    private String Resault = "";
    private String info = "<комманда> узнать что выполняет данная команда.";
    @Override
    public String execute(Update update,  String[] args) {
        Resault = "";
        HashMap<String, Command> commandMap = new CommandHandler().getCommandMap();
        if (args.length == 1) {
            for (HashMap.Entry<String, Command> temp : commandMap.entrySet()) {
                String key = temp.getKey();
                Command value = temp.getValue();
                Resault = Resault + key + " - " + value.getInfo() + "\n";
            }
        } else if (args.length == 2) {
            String searchCommand = args[1];
            Resault = "/" + args[1] + " - " + commandMap.get("/" + args[1]).getInfo();
        } else {
            Resault = "Извините, я не нашёл команду, которую вы ищите";
        }
        return Resault;
    }
    public String getInfo(){
        return info;
    }
}
