package org.kirillandrey.dialogsService.controller;

import java.util.ArrayList;
/**
 * Класс {@code Node} представляет узел в графе диалоговых сценариев.
 * Узел содержит ссылку на диалог, ключ состояния, родительский узел и список дочерних узлов.
 */
public class Node {
    private Dialog dialog;
    private String keyState;
    private Node parent;
    private ArrayList<Node> children;

    /**
     * Конструктор класса {@code Node}.
     *
     * @param dialog    объект диалога
     * @param keyState  ключ состояния узла
     */
    public Node(Dialog dialog, String keyState) {
        this.dialog = dialog;
        this.keyState = keyState;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    /**
     * Устанавливает родительский узел для текущего узла.
     *
     * @param parent родительский узел
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Добавляет дочерний узел к текущему узлу.
     *
     * @param child дочерний узел
     */
    public void addChild(Node child) {
        this.children.add(child);
    }

    /**
     * Получает объект диалога, связанного с текущим узлом.
     *
     * @return объект диалога
     */
    public Dialog getData() {
        return dialog;
    }

    /**
     * Получает родительский узел текущего узла.
     *
     * @return родительский узел
     */
    public Node getParent() {
        return parent;
    }
    public ArrayList<Node> getChild() {
        return children;
    }

    /**
     * Получает ключ состояния текущего узла.
     *
     * @return ключ состояния
     */
    public String getKeyState(){
        return keyState;
    }

    /**
     * Ищет следующий узел на основе команды.
     *
     * @param command команда от пользователя
     * @return следующий узел или {@code null}, если не найден
     */
    public Node findNextNode(String command) {
        String new_command = command.toLowerCase();
        if (new_command.equals("назад") && parent != null) {
            return parent;
        }
        for (Node child : children) {
            if (child.dialog.getKey() == null || child.dialog.getKey().equals(new_command)) {
                return child;
            }
        }
        return null;
    }

}
