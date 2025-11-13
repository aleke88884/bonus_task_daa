package org.example.models;

import java.util.*;

public class Graph {
    private int vertices;
    private List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    public int getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    // Класс для представления ребра
    public static class Edge implements Comparable<Edge> {
        public int src;
        public int dest;
        public int weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }

        @Override
        public String toString() {
            return "(" + src + " - " + dest + ", weight: " + weight + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return (src == edge.src && dest == edge.dest) ||
                    (src == edge.dest && dest == edge.src);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Math.min(src, dest), Math.max(src, dest));
        }
    }

    // Union-Find структура для алгоритма Крускала
    static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false; // Уже в одном множестве
            }

            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
    }

    // Алгоритм Крускала для построения MST
    public List<Edge> kruskalMST() {
        List<Edge> mst = new ArrayList<>();
        Collections.sort(edges);

        UnionFind uf = new UnionFind(vertices);

        for (Edge edge : edges) {
            if (uf.union(edge.src, edge.dest)) {
                mst.add(edge);
                if (mst.size() == vertices - 1) {
                    break;
                }
            }
        }

        return mst;
    }

    // Найти компоненты связности после удаления ребра
    public Map<Integer, Set<Integer>> findComponents(List<Edge> mstEdges) {
        Map<Integer, Set<Integer>> components = new HashMap<>();
        UnionFind uf = new UnionFind(vertices);

        for (Edge edge : mstEdges) {
            uf.union(edge.src, edge.dest);
        }

        for (int i = 0; i < vertices; i++) {
            int root = uf.find(i);
            components.putIfAbsent(root, new HashSet<>());
            components.get(root).add(i);
        }

        return components;
    }

    // Найти замещающее ребро для восстановления связности
    public Edge findReplacementEdge(List<Edge> mstEdges, Edge removedEdge) {
        // Определяем компоненты после удаления ребра
        Map<Integer, Set<Integer>> components = findComponents(mstEdges);

        System.out.println("  Number of components after removal: " + components.size());

        if (components.size() != 2) {
            System.out.println("  Warning: Expected 2 components, found " + components.size());
            return null; // Граф всё ещё связен или имеет более 2 компонент
        }

        List<Set<Integer>> componentList = new ArrayList<>(components.values());
        Set<Integer> comp1 = componentList.get(0);
        Set<Integer> comp2 = componentList.get(1);

        System.out.println("  Searching for edge between components...");

        // Ищем минимальное ребро между двумя компонентами
        Edge minEdge = null;
        int minWeight = Integer.MAX_VALUE;

        for (Edge edge : edges) {
            // Проверяем, соединяет ли ребро две разные компоненты
            boolean inComp1Src = comp1.contains(edge.src);
            boolean inComp1Dest = comp1.contains(edge.dest);

            if (inComp1Src != inComp1Dest) { // Ребро между компонентами
                System.out.println("    Candidate: " + edge);
                if (edge.weight < minWeight && !edge.equals(removedEdge)) {
                    minWeight = edge.weight;
                    minEdge = edge;
                }
            }
        }

        return minEdge;
    }
}