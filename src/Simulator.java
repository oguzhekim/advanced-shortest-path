import java.util.*;

public class Simulator {
    private record QueueEntry(Vertex vertex, int distance) {
    }
    public int dijkstra(Vertex start, Vertex end, int v) {
        PriorityQueue<QueueEntry> queue = new PriorityQueue<>(Comparator.comparingInt(QueueEntry::distance));
        boolean[] visited = new boolean[v];
        int[] distance = new int[v];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start.index] = 0;
        queue.add(new QueueEntry(start, 0));
        while (!queue.isEmpty()){
            Vertex v1 = queue.poll().vertex;
            if (visited[v1.index])
                continue;
            visited[v1.index] = true;
            for (Edge e: v1.getAdjList()){
                Vertex v2 = e.destination();
                if (visited[v2.index])
                    continue;
                int val = distance[v1.index]+e.weight();
                if (distance[v2.index] > val){
                    distance[v2.index] = val;
                    queue.add(new QueueEntry(v2, val));
                }
            }
            if (v1.equals(end))
                return distance[v1.index];
        }
        return -1;
    }
    public int flag(ArrayList<Vertex> flags, int flagCount, int v){
        int[][] matrix = new int[flagCount][flagCount];
        for (int[] row: matrix)
            Arrays.fill(row, Integer.MAX_VALUE-1);

        for (int i=0; i<flagCount; i++) {
            int visitedFlags = 0;
            Vertex start = flags.get(i); //TODO: Make flags array
            PriorityQueue<QueueEntry> queue = new PriorityQueue<>(Comparator.comparingInt(QueueEntry::distance));
            boolean[] visited = new boolean[v];
            int[] distance = new int[v];
            Arrays.fill(distance, Integer.MAX_VALUE);
            distance[start.index] = 0;
            queue.add(new QueueEntry(start, 0));
            while (!queue.isEmpty()) {
                if (visitedFlags==flagCount) //TODO: Wrong line
                    break;
                Vertex v1 = queue.poll().vertex;
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
                    int val = distance[v1.index]+e.weight();
                    if (distance[v2.index] > val) {
                        distance[v2.index] = val;
                        queue.add(new QueueEntry(v2, val));
                    }
                }
            }
        }

        HashSet<Integer> visitedColumns = new HashSet<>();
        HashSet<Integer> visitedRows = new HashSet<>();
        visitedRows.add(0);
        visitedColumns.add(0);
        int count = 0;
        // Main idea of this matrix is visitedRows are the only rows that can be visited (and should be visited). And visitedColumns cannot be visited again.
        while (visitedColumns.size()!=flagCount){
            int min = Integer.MAX_VALUE;
            int rowToAdd = 0;
            int colToAdd = 0;
            for (int row: visitedRows){
                for (int col=0; col<flagCount; col++){
                    if (!visitedColumns.contains(col)){
                        int cell = matrix[row][col];
                        // If the cell contains this number, it means flags are not connected.
                        if (cell == Integer.MAX_VALUE-1)
                            return -1;
                        // Check the cell of matrix for minimum
                        if (min>cell){
                            min = cell;
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
