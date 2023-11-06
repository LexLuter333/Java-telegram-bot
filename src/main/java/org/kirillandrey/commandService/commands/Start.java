package org.kirillandrey.commandService.commands;

import org.kirillandrey.commandService.controller.Command;

import org.kirillandrey.service.DateBaseHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.SQLException;


public class Start implements Command {
    private String resault;
    private String info = "Авторизация или регистрация в боте.";

    @Override
    public String execute(Long chatid, String[] args) {
        DateBaseHandler DBhendler = new DateBaseHandler();
        String code = DBhendler.signUpUser("?", "?",
                chatid);
        if (code.equals("1")) {
            return "Вы успешно зарегистрированы";
        } else if (code.equals("0")){
            return "Ошибка регистрации";
        }
        return "Вы успешно авторизованы";
    }
    public String getInfo(){
        return info;
    }
}
