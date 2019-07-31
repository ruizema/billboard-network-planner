import java.io.*;
import java.util.*;

public class Tp3 {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String output = args[1];
        Scanner scanner = new Scanner(new File(input));
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        // Counting number of vertices
        ArrayList<String> vertices = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals("---")) {
                break;
            }
            vertices.add(line.split("\\s")[0]);
        }

        // Reading edges
        ArrayList<Edge> edges = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (!line.equals("---")) {
                String[] splitLine = line.split("\\s+|;");
                String name = splitLine[0];
                String vertex1 = splitLine[2];
                String vertex2 = splitLine[3];
                int weight = Integer.parseInt(splitLine[4]);
                edges.add(new Edge(name, vertex1, vertex2, weight));
            }
        }

        // Prim-Jarnik's algorithm to find a minimum spanning tree
        HashMap<String, Integer> keyValue = new HashMap<>();
        HashMap<String, Edge> associatedEdge = new HashMap<>();
        ArrayList<Edge> insideEdges = new ArrayList<>();
        PriorityQueue<String> heap = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int key1 = keyValue.get(o1);
                int key2 = keyValue.get(o2);
                return key1 - key2;
            }
        });
        for (String vertex : vertices) {
            keyValue.put(vertex, Integer.MAX_VALUE);
            associatedEdge.put(vertex, null);
            heap.add(vertex);
        }
        keyValue.put(vertices.get(0), 0);
        while (!heap.isEmpty()) {
            String vertex = heap.remove();
            Edge edge = associatedEdge.get(vertex);
            if (edge != null && !insideEdges.contains(edge)) {
                insideEdges.add(edge);
            }
            for (Edge e : edges) {
                String[] edgeVertices = e.getVertices();
                String vertex1 = edgeVertices[0];
                String vertex2 = edgeVertices[1];
                if (vertex.equals(vertex1) && heap.contains(vertex2)) {
                    if (e.getWeight() < keyValue.get(vertex2)) {
                        keyValue.put(vertex2, e.getWeight());
                        associatedEdge.put(vertex2, e);
                        heap.remove(vertex2);
                        heap.add(vertex2);
                    }
                } else if (vertex.equals(vertex2) && heap.contains(vertex1)) {
                    if (e.getWeight() < keyValue.get(vertex1)) {
                        keyValue.put(vertex1, e.getWeight());
                        associatedEdge.put(vertex1, e);
                        heap.remove(vertex1);
                        heap.add(vertex1);
                    }
                }
            }
        }
        Collections.sort(insideEdges);

        // Writing to file
        for (String vertex : vertices) {
            writer.write(vertex + '\n');
        }
        int total = 0;
        for (Edge edge : insideEdges) {
            writer.write(edge.toString() + '\n');
            total += edge.getWeight();
        }
        writer.write("---\n" + total);
        writer.close();
    }
}
