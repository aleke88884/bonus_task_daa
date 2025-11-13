import org.example.models.Graph;

import java.util.*;

/**
 * Тестовый класс для проверки работы MST
 * Показывает детальную информацию о каждом шаге
 */
public class TestMST {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   MST EDGE REMOVAL - DETAILED TEST & VALIDATION   ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        // Test 1: Простой граф
        System.out.println("TEST 1: Simple Graph (6 vertices, 9 edges)");
        System.out.println("═══════════════════════════════════════════════════");
        testSimpleGraph();

        System.out.println("\n\n");

        // Test 2: Более сложный граф
        System.out.println("TEST 2: Complex Graph (7 vertices, 12 edges)");
        System.out.println("═══════════════════════════════════════════════════");
        testComplexGraph();
    }

    private static void testSimpleGraph() {
        // Создаём граф
        Graph graph = new Graph(6);

        System.out.println("\n📊 CREATING GRAPH:");
        System.out.println("Vertices: 0, 1, 2, 3, 4, 5");
        System.out.println("\nAdding edges:");

        addAndPrint(graph, 0, 1, 4);
        addAndPrint(graph, 0, 2, 3);
        addAndPrint(graph, 1, 2, 1);
        addAndPrint(graph, 1, 3, 2);
        addAndPrint(graph, 2, 3, 4);
        addAndPrint(graph, 3, 4, 2);
        addAndPrint(graph, 3, 5, 6);
        addAndPrint(graph, 4, 5, 3);
        addAndPrint(graph, 2, 4, 5);

        // Построение MST
        System.out.println("\n\n🌲 STEP 1: BUILDING MST (Kruskal's Algorithm)");
        System.out.println("─────────────────────────────────────────────");
        List<Graph.Edge> mst = graph.kruskalMST();

        System.out.println("MST edges (sorted by addition order):");
        int totalWeight = 0;
        for (int i = 0; i < mst.size(); i++) {
            Graph.Edge edge = mst.get(i);
            System.out.printf("  [%d] %s\n", i, edge);
            totalWeight += edge.weight;
        }
        System.out.println("✓ Total MST weight: " + totalWeight);
        System.out.println("✓ Number of edges: " + mst.size() + " (expected: " + (graph.getVertices() - 1) + ")");

        // Визуализация MST
        visualizeMST(mst);

        // Удаление ребра
        System.out.println("\n\n✂️  STEP 2: REMOVING EDGE");
        System.out.println("─────────────────────────────────────────────");
        Graph.Edge removedEdge = mst.get(2);
        System.out.println("Removing edge at index [2]: " + removedEdge);

        List<Graph.Edge> mstAfterRemoval = new ArrayList<>(mst);
        mstAfterRemoval.remove(removedEdge);

        System.out.println("\nRemaining MST edges:");
        for (Graph.Edge edge : mstAfterRemoval) {
            System.out.println("  " + edge);
        }

        // Компоненты
        System.out.println("\n\n🔍 STEP 3: ANALYZING COMPONENTS");
        System.out.println("─────────────────────────────────────────────");
        Map<Integer, Set<Integer>> components = graph.findComponents(mstAfterRemoval);

        int compNum = 1;
        for (Set<Integer> component : components.values()) {
            List<Integer> sorted = new ArrayList<>(component);
            Collections.sort(sorted);
            System.out.println("Component " + compNum + ": " + sorted);
            compNum++;
        }
        System.out.println("✓ Total components: " + components.size());

        // Поиск замещающего ребра
        System.out.println("\n\n🔎 STEP 4: FINDING REPLACEMENT EDGE");
        System.out.println("─────────────────────────────────────────────");
        Graph.Edge replacementEdge = graph.findReplacementEdge(mstAfterRemoval, removedEdge);

        if (replacementEdge != null) {
            System.out.println("✓ Replacement edge found: " + replacementEdge);

            // Новый MST
            System.out.println("\n\n🌳 STEP 5: NEW MST AFTER RECONNECTION");
            System.out.println("─────────────────────────────────────────────");
            List<Graph.Edge> newMST = new ArrayList<>(mstAfterRemoval);
            newMST.add(replacementEdge);

            System.out.println("New MST edges:");
            int newTotalWeight = 0;
            for (Graph.Edge edge : newMST) {
                System.out.println("  " + edge);
                newTotalWeight += edge.weight;
            }
            System.out.println("\n✓ New total weight: " + newTotalWeight);
            System.out.println("✓ Weight difference: +" + (newTotalWeight - totalWeight));
            System.out.println("\n✅ SUCCESS: Graph successfully reconnected!");

            // Проверка связности
            Map<Integer, Set<Integer>> finalComponents = graph.findComponents(newMST);
            System.out.println("✓ Final components: " + finalComponents.size() + " (should be 1)");

        } else {
            System.out.println("❌ ERROR: No replacement edge found!");
        }
    }

    private static void testComplexGraph() {
        Graph graph = new Graph(7);

        System.out.println("\n📊 CREATING COMPLEX GRAPH:");
        System.out.println("Vertices: 0, 1, 2, 3, 4, 5, 6");
        System.out.println("\nAdding edges:");

        addAndPrint(graph, 0, 1, 2);
        addAndPrint(graph, 0, 3, 6);
        addAndPrint(graph, 1, 2, 3);
        addAndPrint(graph, 1, 3, 8);
        addAndPrint(graph, 1, 4, 5);
        addAndPrint(graph, 2, 4, 7);
        addAndPrint(graph, 3, 4, 9);
        addAndPrint(graph, 3, 5, 4);
        addAndPrint(graph, 4, 5, 2);
        addAndPrint(graph, 4, 6, 1);
        addAndPrint(graph, 5, 6, 3);
        addAndPrint(graph, 2, 6, 6);

        List<Graph.Edge> mst = graph.kruskalMST();
        System.out.println("\n🌲 MST built with " + mst.size() + " edges");

        for (int i = 0; i < mst.size(); i++) {
            System.out.printf("  [%d] %s\n", i, mst.get(i));
        }

        // Удаляем другое ребро
        Graph.Edge removedEdge = mst.get(4);
        System.out.println("\n✂️  Removing: " + removedEdge);

        List<Graph.Edge> mstAfterRemoval = new ArrayList<>(mst);
        mstAfterRemoval.remove(removedEdge);

        Graph.Edge replacement = graph.findReplacementEdge(mstAfterRemoval, removedEdge);
        System.out.println(replacement != null ?
                "✓ Replacement found: " + replacement :
                "❌ No replacement found");
    }

    private static void addAndPrint(Graph graph, int src, int dest, int weight) {
        graph.addEdge(src, dest, weight);
        System.out.printf("  %d -- %d  (weight: %d)\n", src, dest, weight);
    }

    private static void visualizeMST(List<Graph.Edge> mst) {
        System.out.println("\n📈 MST Visualization:");
        System.out.println("  (Vertices connected by edges)\n");

        Set<Integer> vertices = new HashSet<>();
        for (Graph.Edge edge : mst) {
            vertices.add(edge.src);
            vertices.add(edge.dest);
        }

        System.out.println("  Edges in MST:");
        for (Graph.Edge edge : mst) {
            System.out.println("    " + edge.src + " ═══[" + edge.weight + "]═══ " + edge.dest);
        }
    }
}
