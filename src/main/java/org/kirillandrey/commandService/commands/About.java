package org.kirillandrey.commandService.commands;

import org.kirillandrey.commandService.controller.Command;

public class About implements Command {
    private String resault = "Наш бот погоды \" SunBot \" предоставляет вам актуальную информацию о погоде в любом месте, используя вашу геолокацию. Вот, как вы можете взаимодействовать с ботом:\n1) Получение погоды по текущей геолокации\n2) Получение погоды в другом месте\n3) Настройки единиц измерения\n4) Подписка на уведомления";
    private String info = "Узнать подробнее о боте и его функциях.";

    @Override
    public String execute(Long chatid, String[] args) {
        return resault;
    }
    public String getInfo(){
        return info;
    }
}
