package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Authors implements Command {
    private String Resault = "Авторы: Андрей Селин (adrezoff)\nБоршов Кирилл (LexLuter333)";
    private String info = "Посмотреть список авторов бота.";

    @Override
    public String execute(Update update, String[] args) {
        return Resault;
    }
    public String getInfo(){
        return info;
    }
}
