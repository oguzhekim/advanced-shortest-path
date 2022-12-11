import java.util.*;

public class RaceDaySimulation {
    public void simulate(PriorityQueue<Vertex> queue) {
        HashSet<Vertex> visited = new HashSet<>();
        // Initially only starting vertex is in the queue.
        while (!queue.isEmpty()){
            Vertex v1 = queue.poll();
            visited.add(v1);
            for (Edge e: v1.getAdjList()){
                Vertex v2 = e.destination();
                if (!visited.contains(v2) && v2.getShortestPath() > v1.getShortestPath()+e.weight()){
                    queue.remove(v2);
                    v2.setShortestPath(v1.getShortestPath()+e.weight());
                    queue.add(v2);
                }
            }
        }
    }
}
