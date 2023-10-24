package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.kirillandrey.service.DateBaseHandler;

import java.sql.SQLException;


public class Start implements Command {
    private String Resault;
    private String info = "Начальное приветствие бота.";

    @Override
    public String execute(Update update, String[] args) {
        DateBaseHandler DBhendler = new DateBaseHandler();
        if (DBhendler.findUser(update.getMessage().getChatId()) == true){
            Resault = "Вы успешно авторизованы";
        } else {
            try {
                DBhendler.singUpUser(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getLastName(),
                        update.getMessage().getChatId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Resault = "Вы успешно зарегестрированы";
        }
        return Resault;
    }
    public String getInfo(){
        return info;
    }
}
