package org.kirillandrey.commands;

import org.kirillandrey.service.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public class commandStart implements Command {
    private String info;

    public commandStart() {
        this.info = "Hello, I am TELEGRAM BOT.";
    }

    @Override
    public String execute(Update update) {
        return info;
    }
}
