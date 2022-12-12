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
        int[][] matrix = new int[flagCount][flagCount];
        for (int i=0; i<flagCount; i++){
            for (Vertex v: vertices.values())
                v.setShortestPath(Integer.MAX_VALUE);
            flags.get(i).setShortestPath(0);
            queue.clear();
            queue.add(flags.get(i));
            HashSet<Vertex> visited = new HashSet<>();
            while (!queue.isEmpty()){
                Vertex v1 = queue.peek();
                if (v1.isFlag() && !v1.equals(flags.get(i))){
                    matrix[i][flags.indexOf(v1)] = v1.getShortestPath();
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
    private void deleteColumn(int[][] matrix, int col){

    }
}
