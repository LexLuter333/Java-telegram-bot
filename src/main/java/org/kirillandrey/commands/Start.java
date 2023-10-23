package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class Start implements Command {
    private String Resautl = "Привет, я WeatherBot!";
    private String info = "Начальное приветствие бота.";

    @Override
    public String execute(Update update, String[] args) {
        return Resautl;
    }
    public String getInfo(){
        return info;
    }
}
