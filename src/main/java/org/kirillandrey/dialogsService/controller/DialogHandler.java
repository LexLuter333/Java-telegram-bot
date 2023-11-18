package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.service.DateBaseHandler;

import java.util.List;

public class DialogHandler {
    private Graph graphDialog;
    public DialogHandler(){
        this.graphDialog = new Graph();
    }
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
    public String handleAskDialog(Long chatid, List<String> button){
        String state = new DateBaseHandler().getState(chatid);
        Node node = graphDialog.getNode(state);
        return node.getData().ask(chatid, button);
    }
}