package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class About implements Command {
    private String Resautl = "Данный бот нужен для ... . Он выполняет функции ... , ... , ... . Наша задача ... . ";
    private String info = "Узнать подробнее о боте и его функциях.";

    @Override
    public String execute(Update update, String[] args) {
        return Resautl;
    }
    public String getInfo(){
        return info;
    }
}
