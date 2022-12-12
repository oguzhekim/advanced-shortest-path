import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Graph {
    private final PriorityQueue<Vertex> queue;
    private final HashMap<String, Vertex> vertices;
    public Graph() {
        this.queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getMinVal));
        this.vertices = new HashMap<>();
    }
    public PriorityQueue<Vertex> getQueue() {
        return queue;
    }

    public HashMap<String, Vertex> getVertices() {
        return vertices;
    }
}
