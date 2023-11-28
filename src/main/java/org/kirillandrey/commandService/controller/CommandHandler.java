package org.kirillandrey.commandService.controller;

import org.kirillandrey.commandService.commands.*;

import java.sql.SQLException;
import java.util.HashMap;
/**
 * Класс CommandHandler обрабатывает входящие команды от пользователей.
 * Он содержит отображение команд (строковых) на соответствующие объекты команд (реализации интерфейса Command).
 */
public class CommandHandler{
    private HashMap<String, Command> commandMap = new HashMap<>();

    /**
     * Конструктор CommandHandler инициализирует отображение команд и их соответствующих объектов.
     */
    public CommandHandler() {
        commandMap.put("/start", new Start());
        commandMap.put("/authors", new Authors());
        commandMap.put("/about", new About());
        commandMap.put("/weather", new Weather());
        commandMap.put("/help", new Help());
    }

    /**
     * Обрабатывает входящую команду, выполняя соответствующую команду, если она существует.
     *
     * @param chatid  Уникальный идентификатор чата или пользователя.
     * @param command Строка, представляющая входящую команду.
     * @param args    Аргументы, связанные с командой.
     * @return Строка, представляющая результат или ответ выполнения команды.
     * @throws SQLException Исключение, связанное с работой с базой данных.
     */
    public String handleCommand(Long chatid, String command, String[] args) throws SQLException {
        Command cmd = commandMap.get(command);
        if (cmd != null) {
           return cmd.execute(chatid, args);
        } else {
            return "Извините, я не понимаю вашей команды";
        }
    }

    /**
     * Получает отображение команд и их соответствующих объектов.
     *
     * @return Отображение команд на объекты команд.
     */
    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
