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
        if (DBhendler.userIsDB(chatid) == true){
            resault = "Вы успешно авторизованы.";
        } else {
            try {
                DBhendler.singUpUser("?", "?",
                        chatid);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resault = "Вы успешно зарегестрированы.";
        }
        return resault;
    }
    public String getInfo(){
        return info;
    }
}
