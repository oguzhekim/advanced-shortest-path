import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class project4 {
    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        HashMap<String, Vertex> vertices = graph.getVertices();
        PriorityQueue<Vertex> queue = graph.getQueue();
        ArrayList<Vertex> flags = new ArrayList<>();//TODO: Make this hashset
        File input = new File("largeCases/input/stress1.txt");
        Scanner reader = new Scanner(input);
        int vertexCount = Integer.parseInt(reader.nextLine());
        int flagCount = Integer.parseInt(reader.nextLine());
        String[] thirdLine = reader.nextLine().split(" ");
        Vertex startVertex = new Vertex(thirdLine[0]);
        Vertex finishVertex = new Vertex(thirdLine[1]);
        startVertex.setStart(true);
        startVertex.setShortestPath(0);
        finishVertex.setFinish(true);
        vertices.put(thirdLine[0], startVertex);
        vertices.put(thirdLine[1], finishVertex);
        queue.add(startVertex);
//        queue.add(finishVertex);
        String[] fourthLine = reader.nextLine().split(" ");
        // Create vertices with flags
        for (String s : fourthLine) {
            if (startVertex.getName().equals(s)) {
                startVertex.setFlag(true);
                flags.add(startVertex);//
            } else if (finishVertex.getName().equals(s)) {
                finishVertex.setFlag(true);
                flags.add(finishVertex);//
            } else {
                Vertex v = new Vertex(s);
                v.setFlag(true);
                vertices.put(s, v);
                flags.add(v); //
//                queue.add(v);
            }
        }
        for (int i=0; i<vertexCount; i++){
            String[] line = reader.nextLine().split(" ");
            Vertex v1 = vertices.get(line[0]);
            // If vertex not in vertexList.
            if (v1 == null){
                v1 = new Vertex(line[0]);
                vertices.put(line[0], v1);
            }
            for (int j=1; j<line.length; j+=2){
                Vertex v2;
                if (!vertices.containsKey(line[j])){
                    v2 = new Vertex(line[j]);
                    vertices.put(line[j], v2);
//                    queue.add(v2);
                }
                else
                    v2 = vertices.get(line[j]);
                v1.adjAdd(new Edge(v2, Integer.parseInt(line[j+1])));
                v2.adjAdd(new Edge(v1, Integer.parseInt(line[j+1])));
            }
        }
        RaceDaySimulation s = new RaceDaySimulation();
        s.simulate(queue);
        int raceShortestPath = vertices.get(finishVertex.getName()).getShortestPath();
        if (raceShortestPath==Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(raceShortestPath);

        // TODO:Flags
        // Reset vertices
        System.out.println(s.flag(queue, flags, flagCount, vertices));


    }
}
