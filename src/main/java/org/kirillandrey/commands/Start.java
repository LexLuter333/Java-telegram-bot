package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.kirillandrey.service.DateBaseHandler;

import java.sql.SQLException;


public class Start implements Command {
    private String resault;
    private String info = "Авторизация или регистрация в боте.";

    @Override
    public String execute(Update update, String[] args) {
        DateBaseHandler DBhendler = new DateBaseHandler();
        if (DBhendler.userIsDB(update.getMessage().getChatId()) == true){
            resault = "Вы успешно авторизованы.";
        } else {
            try {
                DBhendler.singUpUser(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getLastName(),
                        update.getMessage().getChatId());
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
