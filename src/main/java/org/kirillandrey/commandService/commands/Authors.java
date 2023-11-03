package org.kirillandrey.commandService.commands;

import org.kirillandrey.commandService.controller.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Authors implements Command {
    private String resault = "Авторы: Андрей Селин (adrezoff)\nБоршов Кирилл (LexLuter333)";
    private String info = "Посмотреть список авторов бота.";

    @Override
    public String execute(Update update, String[] args) {
        return resault;
    }
    public String getInfo(){
        return info;
    }
}
