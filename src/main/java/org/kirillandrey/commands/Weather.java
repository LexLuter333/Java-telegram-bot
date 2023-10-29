package org.kirillandrey.commands;

import org.kirillandrey.WeatherBot.WeatherJsonParser;
import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Weather implements Command {
    private String resautl;

    private String info = "<Город> Выводит данные о погоде в данном городе";

    public String execute(Update update, String[] args) {

        if (args.length > 1){
            String  City = "";
            for (int i = 1; i < args.length; i++){
                City = City + args[i];
                if (i != args.length-1) City = City + " ";
            }
            resautl = WeatherJsonParser.getReadyForecast(City);
        } else {
            resautl = "Ошибка ввода локации (Пример: /weather Екатеринбург или Ekaterinburg)";
        }

        return resautl;
    }
    public String getInfo(){
        return info;
    }

}
