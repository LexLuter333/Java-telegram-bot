package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.service.DateBaseHandler;

import java.util.List;
/**
 * Класс {@code DialogHandler} отвечает за обработку диалоговых сценариев.
 * Взаимодействует с классом {@code DateBaseHandler} для работы с базой данных.
 */
public class DialogHandler {
    private Graph graphDialog;
    /**
     * Конструктор класса, инициализирующий объект {@code DialogHandler}.
     */
    public DialogHandler(){
        this.graphDialog = new Graph();
    }
    /**
     * Обрабатывает ответ в диалоге
     *
     * @param command команда, полученная от пользователя
     * @param chatid  идентификатор чата пользователя
     * @return строка ответа на команду
     */
    public String handleAnswerDialog(String command, Long chatid) {
        String state = new DateBaseHandler().getState(chatid);
        Node node = graphDialog.getNode(state);

        if ("назад".equalsIgnoreCase(command)) {
            Node parent = node.getParent();
            if (parent != null) {
                new DateBaseHandler().setState(chatid, parent.getKeyState());
                return "";
            }
        }

        Node nextNode = node.findNextNode(command);
        if (new DateBaseHandler().setState(chatid, nextNode.getKeyState())) {
            return node.getData().answer(command, chatid);
        }
        return "Ошибка, попробуйте позже";
    }
    /**
     * Обрабатывает вопрос пользователю в диалоге
     *
     * @param chatid идентификатор чата пользователя
     * @param button список кнопок, доступных пользователю
     * @return строка ответа на запрос
     */
    public String handleAskDialog(Long chatid, List<String> button){
        String state = new DateBaseHandler().getState(chatid);
        Node node = graphDialog.getNode(state);
        return node.getData().ask(chatid, button);
    }
}