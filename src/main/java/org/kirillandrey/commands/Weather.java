package org.kirillandrey.commands;

import org.kirillandrey.WeatherBot.WeatherJsonParser;
import org.kirillandrey.service.Command;
import org.kirillandrey.service.CommandHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class Weather {
    String Resautl = WeatherJsonParser.getReadyForecast("Berlin");

    private String info = "Выводит данные о погоде в данном городе";

    public String execute(Update update, String[] args) {
        return Resautl;
    }
    public String getInfo(){
        return info;
    }

}
