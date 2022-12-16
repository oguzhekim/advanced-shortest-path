import java.util.*;

public class Simulator {
    // Entry that will enter PQ of Dijkstra.
    private record QueueEntry(Vertex vertex, int distance) {
    }
    public int dijkstra(Vertex start, Vertex end, int v) {
        PriorityQueue<QueueEntry> queue = new PriorityQueue<>(Comparator.comparingInt(QueueEntry::distance));
        // Every vertex has an index field that points them to the arrays below.
        // At the beginning all the nodes are unvisited.
        boolean[] visited = new boolean[v];
        // Distances set to infinity at the beginning.
        int[] distance = new int[v];
        Arrays.fill(distance, Integer.MAX_VALUE);
        // Enqueue start vertex and set distance to 0.
        distance[start.index] = 0;
        queue.add(new QueueEntry(start, 0));
        while (!queue.isEmpty()){
            Vertex v1 = queue.poll().vertex;
            // I'm not doing queue.remove() operations, so there might be multiple entries of same vertex in the queue.
            // So I'm checking if that node is visited to improve performance.
            if (visited[v1.index])
                continue;
            visited[v1.index] = true;
            // Iterate edges of current vertex.
            for (Edge e: v1.getAdjList()){
                Vertex v2 = e.destination();
                // This block further increases performance by skipping that edge if the destination is already visited.
                if (visited[v2.index])
                    continue;
                // We will enqueue a new entry if a new shortest path is found.
                int val = distance[v1.index]+e.weight();
                if (distance[v2.index] > val){
                    distance[v2.index] = val;
                    queue.add(new QueueEntry(v2, val));
                }
            }
            // We don't need to find the shortest path to all vertices. Once end vertex is visited we can stop Dijkstra to improve performance.
            if (v1.equals(end))
                return distance[v1.index];
        }
        // If queue becomes empty without visiting end vertex, that means end vertex is not connected to the start vertex.
        return -1;
    }
    public int flag(ArrayList<Vertex> flags, int flagCount, int v){
        int[][] matrix = new int[flagCount][flagCount];
        for (int[] row: matrix)
            Arrays.fill(row, Integer.MAX_VALUE-1);
        // Run Dijkstra for each flag to other flags and store the distances to matrix above.
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
        // Shortest distance from a flag to any other flag is determined and stored in matrix.
        HashSet<Integer> rowsToVisit = new HashSet<>();
        rowsToVisit.add(0);
        int count = 0;
        // Main idea of this matrix is rowsToVisit are the only rows that can be visited (and should be visited). And visitedColumns cannot be visited again.
        // So start with row 0, and we can't visit column 0 as well because we don't want to consider distance of a flag to itself.
        // For inp8 initial matrix is:
        //          s3 s6  s7
        //     s3   |0  3  6|
        //     s6   |3  0  4|
        //     s7   |6  4  0|
        // We start with s3 and get the minimum cell. But we do not consider column containing s3 because we are already visiting it right now.
        //          s3 s6  s7
        //     s3   |x  3  6|
        //     s6   |x  0  4|
        //     s7   |x  4  0|
        // Minimum is 3 on the step above, it's added to count.
        // Since minimum corresponds to s6, now we will visit both s3 and s6.
        // But we won't consider columns of s3 and s6 again because these vertices are visited and added to count.
        //          s3 s6  s7
        //     s3   |x  x  6|
        //     s6   |x  x  4|
        //     s7   |x  x  0|
        // Minimum is 4 on the step above, it's added to count.
        // Since minimum corresponds to s7, now we will visit both s3, s6 and s7.
        // But we won't consider columns of s3, s6 and s7 again because these vertices are visited and added to count.
        //          s3 s6  s7
        //     s3   |x  x  x|
        //     s6   |x  x  x|
        //     s7   |x  x  x|
        // Since there no cell left to visit code will terminate and return count as 7.
        while (rowsToVisit.size()!=flagCount){
            int min = Integer.MAX_VALUE;
            int rowToAdd = 0;
            for (int row: rowsToVisit){
                for (int col=0; col<flagCount; col++){
                    if (!rowsToVisit.contains(col)){
                        int cell = matrix[row][col];
                        // If the cell contains this number, it means flags are not connected.
                        if (cell == Integer.MAX_VALUE-1)
                            return -1;
                        // Check the cell of matrix for minimum
                        if (min>cell){
                            min = cell;
                            rowToAdd = col;
                        }
                    }
                }
            }
            rowsToVisit.add(rowToAdd);
            count+=min;
        }
        return count;
    }
}
