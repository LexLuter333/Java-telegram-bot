package org.kirillandrey.service;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.SQLException;
import java.util.HashMap;

public interface Command {
    String execute(Update updates, String[] args);
    String getInfo();
}