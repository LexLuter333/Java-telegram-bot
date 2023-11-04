package org.kirillandrey.dialogsService.controller;

import java.util.ArrayList;

public class Node {
    private Dialog dialog;
    private String keyState;
    private Node parent;
    private ArrayList<Node> children;

    public Node(Dialog dialog, String keyState) {
        this.dialog = dialog;
        this.keyState = keyState;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public Dialog getData() {
        return dialog;
    }

    public Node getParent() {
        return parent;
    }
    public String getKeyState(){
        return keyState;
    }
    public Node findNextNode(String command) {
        if (command.equals("назад")){
            return parent;
        }
        for (Node i: children){
            if (i.dialog.getKey().equals(command) || i.dialog.getKey()!= null) {
                return i;
            }
        }
        return null;
    }
}
