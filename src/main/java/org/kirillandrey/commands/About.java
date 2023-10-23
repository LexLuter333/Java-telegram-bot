package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class About implements Command {
    private String Resautl = "Наш бот погоды предоставляет вам актуальную информацию о погоде в любом месте, используя вашу геолокацию. Вот, как вы можете взаимодействовать с ботом:\n1) Получение погоды по текущей геолокации\n2) Получение погоды в другом месте\n3) Настройки единиц измерения\n4) Подписка на уведомления";
    private String info = "Узнать подробнее о боте и его функциях.";

    @Override
    public String execute(Update update, String[] args) {
        return Resautl;
    }
    public String getInfo(){
        return info;
    }
}
