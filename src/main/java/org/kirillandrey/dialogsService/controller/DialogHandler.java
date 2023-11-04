package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.service.DateBaseHandler;

import java.util.List;

public class DialogHandler {
    private Graph graphDialog = new Graph();
    public String handleAnswerDialog(String command, Long chatid) {
        String state = new DateBaseHandler().getState(chatid);
        Node node = graphDialog.getNode(state);
        Node nextNode = node.findNextNode(state);
        if (new DateBaseHandler().setState(chatid, nextNode.getKeyState())) {
            return nextNode.getData().answer(command, chatid);
        } else {
            return "Ошибка, попробуйте позже";
        }
    }
    public String handleAskDialog(Long chatid, List<String> button){
        String state = new DateBaseHandler().getState(chatid);
        Node node = graphDialog.getNode(state);
        return node.getData().ask(button);
    }
}