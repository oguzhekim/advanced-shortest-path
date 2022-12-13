import java.util.*;

public class RaceDaySimulation {
    public int dijkstra(Vertex start, Vertex end, int v) {
        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getMinVal));
        boolean[] visited = new boolean[v];
        int[] distance = new int[v];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start.index] = 0;
        start.minVal = 0;
        queue.add(start);
        while (!queue.isEmpty()){
            Vertex v1 = queue.poll();
            visited[v1.index] = true;
//            if (visited[v1.index])
//                continue;
            if (distance[v1.index] < v1.getMinVal()) continue; //
            for (Edge e: v1.getAdjList()){
                Vertex v2 = e.destination();
                if (visited[v2.index])
                    continue;
                int val = distance[v1.index]+e.weight();
                if (distance[v2.index] > val){
                    distance[v2.index] = val;
                    v2.minVal = val;
                    queue.add(v2);
                }
            }
            if (v1.equals(end))
                return distance[v1.index];
        }
        return -1;
    }
    public int flag(ArrayList<Vertex> flags, int flagCount, int v){
        int[][] matrix = new int[flagCount][flagCount];

        for (int i=0; i<flagCount; i++) {
            int visitedFlags = 0; //TODO: Added performancewise but didn't change much
            Vertex start = flags.get(i); //TODO: Make flags array
            PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getMinVal));
            boolean[] visited = new boolean[v];
            int[] distance = new int[v];
            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[start.index] = 0;
            start.minVal = 0;
            queue.add(start);
            while (!queue.isEmpty()) {
                if (visitedFlags==flagCount) //TODO: Wrong line
                    break;
                Vertex v1 = queue.poll();
                if (visited[v1.index]) continue;
                visited[v1.index] = true;
                if (v1.isFlag()){
                    visitedFlags++;
                    matrix[i][flags.indexOf(v1)] = distance[v1.index];
                }
                for (Edge e : v1.getAdjList()) {
                    Vertex v2 = e.destination();
                    if (visited[v2.index])
                        continue;
                    if (distance[v2.index] > distance[v1.index] + e.weight()) {
                        distance[v2.index] = distance[v1.index] + e.weight();
                        v2.minVal = distance[v1.index] + e.weight();
                        queue.add(v2);
                    }
                }
            }
        }

        HashSet<Integer> visitedColumns = new HashSet<>();
        HashSet<Integer> visitedRows = new HashSet<>();
        visitedRows.add(0);
        visitedColumns.add(0);
        int count = 0;
        while (visitedColumns.size()!=flagCount){
            int min = Integer.MAX_VALUE;
            int rowToAdd = 0;
            int colToAdd = 0;
            for (int row: visitedRows){
                for (int col=0; col<flagCount; col++){
                    if (!visitedColumns.contains(col)){
                        // get min of that row
                        if (min>matrix[row][col]){
                            min = matrix[row][col];
                            rowToAdd = col;
                            colToAdd = col;
                        }
                    }
                }
            }
            visitedRows.add(rowToAdd);
            visitedColumns.add(colToAdd);
            count+=min;
        }
        return count;
    }
}
