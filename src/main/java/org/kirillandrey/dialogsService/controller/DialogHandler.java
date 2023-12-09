package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.service.DateBaseHandler;

import java.util.List;
/**
 * Класс {@code DialogHandler} отвечает за обработку диалоговых сценариев.
 * Взаимодействует с классом {@code DateBaseHandler} для работы с базой данных.
 */
public class DialogHandler {
    private Graph graphDialog;
    private DateBaseHandler dateBaseHandler;
    /**
     * Конструктор класса, инициализирующий объект {@code DialogHandler}.
     */
    public DialogHandler(){
        this.graphDialog = new Graph();
        this.dateBaseHandler = new DateBaseHandler();
    }
    public DialogHandler(DateBaseHandler dateBaseHandler){
        this.graphDialog = new Graph();
        this.dateBaseHandler = dateBaseHandler;
    }

    /**
     * Обрабатывает ответ в диалоге
     *
     * @param command команда, полученная от пользователя
     * @param chatid  идентификатор чата пользователя
     * @return строка ответа на команду
     */
    public String handleAnswerDialog(String command, Long chatid) {
        String state = dateBaseHandler.getState(chatid);
        Node node = graphDialog.getNode(state);

        if ("назад".equalsIgnoreCase(command)) {
            Node parent = node.getParent();
            if (parent != null) {
                dateBaseHandler.setState(chatid, parent.getKeyState());
                return "";
            }
        }

        Node nextNode = node.findNextNode(command);
        if (dateBaseHandler.setState(chatid, nextNode.getKeyState())) {
            return node.getData().answer(command, chatid);
        }
        return "";
    }
    /**
     * Обрабатывает вопрос пользователю в диалоге
     *
     * @param chatid идентификатор чата пользователя
     * @param button список кнопок, доступных пользователю
     * @return строка ответа на запрос
     */
    public String handleAskDialog(Long chatid, List<String> button){
        String state = dateBaseHandler.getState(chatid);
        Node node = graphDialog.getNode(state);
        return node.getData().ask(chatid, button);
    }
}