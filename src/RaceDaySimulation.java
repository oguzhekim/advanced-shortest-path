import java.util.*;

public class RaceDaySimulation {
    public void simulate(PriorityQueue<Vertex> queue) {
        HashSet<Vertex> visited = new HashSet<>();
        // Initially only starting vertex is in the queue.
        while (!queue.isEmpty()){
            Vertex v1 = queue.peek();
            visited.add(v1);
            for (Edge e: v1.getAdjList()){
                Vertex v2 = e.destination();
                if (!visited.contains(v2) && v2.getShortestPath() > v1.getShortestPath()+e.weight()){
                    queue.remove(v2);
                    v2.setShortestPath(v1.getShortestPath()+e.weight());
                    queue.add(v2);
                }
            }
            queue.poll();
        }
    }
    public int flag(PriorityQueue<Vertex> queue, ArrayList<Vertex> flags, int flagCount, HashMap<String, Vertex> vertices){
        int count = 0;
        HashSet<Vertex> visitedFlags = new HashSet<>();
        for (int i=0; i<flagCount; i++){
            if (visitedFlags.contains(flags.get(i)))//TODO:Change order with above block
                continue;
            for (Vertex v: vertices.values())
                v.setShortestPath(Integer.MAX_VALUE);
            queue.clear();
            flags.get(i).setShortestPath(0);
            queue.add(flags.get(i));
            visitedFlags.add(flags.get(i));
            HashSet<Vertex> visited = new HashSet<>();
            while (!queue.isEmpty()){
                Vertex v1 = queue.peek();
                if (v1.isFlag() && !visitedFlags.contains(v1)){
                    count += v1.getShortestPath();
//                    visitedFlags.add(v1);
                    break;
                }
                visited.add(v1);
                for (Edge e: v1.getAdjList()){
                    Vertex v2 = e.destination();
                    if (!visited.contains(v2) && v2.getShortestPath() > v1.getShortestPath()+e.weight()){
                        queue.remove(v2);
                        v2.setShortestPath(v1.getShortestPath()+e.weight());
                        queue.add(v2);
                    }
                }
                queue.poll();
            }
            if (visitedFlags.size() == flags.size())
                break;
        }
        return count;
    }
}
