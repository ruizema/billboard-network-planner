public class Edge implements Comparable<Edge> {
    private String name;
    private String[] vertices = new String[2];
    private int weight;

    public Edge(String name, String vertex1, String vertex2, int weight) {
        this.name = name;
        vertices[0] = vertex1;
        vertices[1] = vertex2;
        this.weight = weight;
    }

    public String[] getVertices() {
        return vertices;
    }

    public int getWeight() {
        return weight;
    }

    @Override public String toString() {
        return name + "\t" + vertices[0] + "\t" + vertices[1] + "\t" + weight;
    }

    @Override
    public int compareTo(Edge otherEdge) {
        return this.vertices[0].equals(otherEdge.vertices[0]) ? this.vertices[1].compareTo(otherEdge.vertices[1]) : this.vertices[0].compareTo(otherEdge.vertices[0]);
    }
}
