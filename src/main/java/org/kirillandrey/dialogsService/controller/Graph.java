package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.dialogsService.dialogs.*;
import org.kirillandrey.dialogsService.dialogs.Settings.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс {@code Graph} представляет собой граф диалоговых сценариев.
 * Определены узлы графа и матрица смежности для указания связей между узлами.
 */
public class Graph {
    private HashMap<String, Dialog> table = new HashMap<>();
    private HashMap<String, Node> nodes;
    private int [][] adjacencyMatrix = {
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    };

    /**
     * Конструктор класса {@code Graph} инициализирует узлы графа и связи между ними.
     */
    public Graph() {
        table.put("0", new Menu());
        table.put("1", new WCity());
        table.put("2", new WCityСyrcle());
        table.put("3", new Settings());
        table.put("4", new DefaultSettings());
        table.put("5", new Settings1());
        table.put("6", new Settings2());
        table.put("7", new Settings3());
        table.put("8", new Settings4());
        table.put("9", new Settings5());
        table.put("10", new Settings6());
        table.put("11", new Settings7());
        table.put("12", new Settings8());
        table.put("13", new Settings9());
        table.put("14", new ErrorInput());

        nodes = new HashMap<>();

        for (String key : table.keySet()) {
            Node node = new Node(table.get(key), key);
            nodes.put(key, node);
        }

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    Node parent = nodes.get(String.valueOf(i));
                    Node child = nodes.get(String.valueOf(j));
                    if (parent != null && child != null) {
                        if (child.getParent() == null && j != 0 && j < adjacencyMatrix[i].length-1) {
                            child.setParent(parent);
                        }
                        parent.addChild(child);
                    }
                }
            }
        }
    }

    /**
     * Получает узел по его ключу.
     *
     * @param key ключ узла
     * @return объект {@code Node} или {@code null}, если узел не найден
     */
    public Node getNode(String key) {
        return nodes.get(key);
    }
}
