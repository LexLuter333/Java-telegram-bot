package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.dialogsService.dialogs.*;
import org.kirillandrey.dialogsService.dialogs.Settings.*;

import java.util.HashMap;

public class Graph {
    private HashMap<String, Dialog> Table = new HashMap<>();
    private HashMap<String, Node> nodes;
    private int [][] adjacencyMatrix = {
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},

    };

    public Graph() {
        Table.put("0", new Menu());
        Table.put("1", new WCity());
        Table.put("2", new WCityСyrcle());
        Table.put("3", new Settings());
        Table.put("4", new DefaultSettings());
        Table.put("5", new Settings1());
        Table.put("6", new Settings2());
        Table.put("7", new Settings3());
        Table.put("8", new Settings4());
        Table.put("9", new Settings5());
        Table.put("10", new Settings6());
        Table.put("11", new Settings7());
        Table.put("12", new Settings8());
        Table.put("13", new ErrorInput());

        nodes = new HashMap<>();

        for (String key : Table.keySet()) {
            Node node = new Node(Table.get(key), key);
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
    public Node getNode(String key) {
        return nodes.get(key);
    }

}
