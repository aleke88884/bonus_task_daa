package org.example;

import org.example.models.Graph;

import java.util.*;

public class MSTEdgeRemoval {

    public static void main(String[] args) {
        System.out.println("=== MST Edge Removal and Replacement Demo ===\n");

        // Создаём граф
        Graph graph = createSampleGraph();

        // Шаг 1: Построение MST
        System.out.println("Step 1: Building Minimum Spanning Tree (MST)");
        System.out.println("--------------------------------------------");
        List<Graph.Edge> mst = graph.kruskalMST();

        System.out.println("Original MST edges:");
        int totalWeight = 0;
        for (Graph.Edge edge : mst) {
            System.out.println("  " + edge);
            totalWeight += edge.weight;
        }
        System.out.println("Total MST weight: " + totalWeight);
        System.out.println();

        // Шаг 2: Удаление ребра
        if (mst.isEmpty()) {
            System.out.println("MST is empty!");
            return;
        }

        Graph.Edge removedEdge = mst.get(2); // Удаляем третье ребро
        System.out.println("Step 2: Removing edge from MST");
        System.out.println("--------------------------------------------");
        System.out.println("Removed edge: " + removedEdge);
        System.out.println();

        // Создаём MST без удалённого ребра
        List<Graph.Edge> mstAfterRemoval = new ArrayList<>(mst);
        mstAfterRemoval.remove(removedEdge);

        // Шаг 3: Показываем компоненты после удаления
        System.out.println("Step 3: Components after edge removal");
        System.out.println("--------------------------------------------");
        Map<Integer, Set<Integer>> components = graph.findComponents(mstAfterRemoval);

        int componentNum = 1;
        for (Set<Integer> component : components.values()) {
            System.out.println("Component " + componentNum + ": " + component);
            componentNum++;
        }
        System.out.println();

        // Шаг 4: Поиск замещающего ребра
        System.out.println("Step 4: Finding replacement edge");
        System.out.println("--------------------------------------------");
        Graph.Edge replacementEdge = graph.findReplacementEdge(mstAfterRemoval, removedEdge);

        if (replacementEdge != null) {
            System.out.println("Replacement edge found: " + replacementEdge);
            System.out.println();

            // Показываем новый MST
            List<Graph.Edge> newMST = new ArrayList<>(mstAfterRemoval);
            newMST.add(replacementEdge);

            System.out.println("Step 5: New MST after reconnection");
            System.out.println("--------------------------------------------");
            System.out.println("New MST edges:");
            int newTotalWeight = 0;
            for (Graph.Edge edge : newMST) {
                System.out.println("  " + edge);
                newTotalWeight += edge.weight;
            }
            System.out.println("Total new MST weight: " + newTotalWeight);
            System.out.println();

            System.out.println("✓ Graph successfully reconnected!");
        } else {
            System.out.println("✗ No replacement edge found!");
        }
    }

    // Создание примерного графа для демонстрации
    private static Graph createSampleGraph() {
        Graph graph = new Graph(6); // 6 вершин (0-5)

        // Добавляем рёбра (вершина1, вершина2, вес)
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);
        graph.addEdge(2, 4, 5);

        System.out.println("Graph created with 6 vertices and 9 edges");
        System.out.println();

        return graph;
    }
}
