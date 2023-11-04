package org.kirillandrey.commandService.commands;

import org.kirillandrey.commandService.controller.Command;

public class Authors implements Command {
    private String resault = "Авторы: Андрей Селин (adrezoff)\nБоршов Кирилл (LexLuter333)";
    private String info = "Посмотреть список авторов бота.";

    @Override
    public String execute(Long chatid, String[] args) {
        return resault;
    }
    public String getInfo(){
        return info;
    }
}
