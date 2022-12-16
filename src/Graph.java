import java.util.HashMap;

public class Graph {
    private final HashMap<String, Vertex> vertices;
    public Graph() {
        this.vertices = new HashMap<>();
    }
    public HashMap<String, Vertex> getVertices() {
        return vertices;
    }
}
