import java.util.*;

public class RaceDaySimulation {
    public int simulate(ArrayList<Vertex> vertexList){
        PriorityQueue<QueueEntry> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));
        ArrayList<QueueEntry> finalList = new ArrayList<>();
        QueueEntry startingPoint = new QueueEntry(vertexList.get(0), 0);
        queue.add(startingPoint);
        finalList.add(startingPoint);
        for (int i =1; i<vertexList.size(); i++){
            QueueEntry entry = new QueueEntry(vertexList.get(i),Integer.MAX_VALUE);
            queue.add(entry);
            finalList.add(entry);
        }
        while (!queue.isEmpty()){
            QueueEntry entry1 = queue.poll();
            for (Edge e: entry1.vertex.getAdjList()){
                Vertex v2 = e.getDestination();
                int distance = e.getWeight();
                QueueEntry entry2 = find(v2, queue);
                if (entry2!=null && entry2.distance > entry1.distance+distance){
                    queue.remove(entry2);
                    entry2.distance = entry1.distance+distance;
                    queue.add(entry2);
                }
            }
        }
        for (QueueEntry e: finalList){
            if (e.vertex.equals(vertexList.get(1)))
                if (e.distance != Integer.MAX_VALUE)
                    return e.distance;
        }
        return -1;
    }
    private QueueEntry find(Vertex v, Queue<QueueEntry> queue){
        for (QueueEntry q: queue){
            if (q.vertex.equals(v))
                return q;
        }
        return null;
    }

    private static class QueueEntry {
        private Vertex vertex;
        private int distance;
        private QueueEntry(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}
