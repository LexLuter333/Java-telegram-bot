package org.kirillandrey.dialogsService.controller;

import org.kirillandrey.dialogsService.dialogs.ErrorInput;
import org.kirillandrey.dialogsService.dialogs.Menu;
import org.kirillandrey.dialogsService.dialogs.WCity;
import org.kirillandrey.dialogsService.dialogs.WCityСyrcle;

import java.util.HashMap;

public class Graph {
    private HashMap<String, Dialog> Table = new HashMap<>();
    private HashMap<String, Node> nodes;
    private int [][] adjacencyMatrix = {
            {0, 1, 0, 1},
            {0, 0, 1, 0},
            {1, 1, 0, 1},
    };

    public Graph() {
        Table.put("0", new Menu());
        Table.put("1", new WCity());
        Table.put("2", new WCityСyrcle());
        Table.put("3", new ErrorInput());

        nodes = new HashMap<>();

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            Node node = new Node(Table.get(i), String.valueOf(i));
            nodes.put(String.valueOf(i + 1), node);
        }

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    Node parent = nodes.get(String.valueOf(i + 1));
                    Node child = nodes.get(String.valueOf(j + 1));
                    child.setParent(parent);
                    parent.addChild(child);
                }
            }
        }
    }

    public Node getNode(String key) {
        return nodes.get(key);
    }
}
