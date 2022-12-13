import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class project4 {
    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int index = 0;
        Graph graph = new Graph(); //TODO: Get rid of vertices map and remove graph class
        HashMap<String, Vertex> vertices = graph.getVertices();
        ArrayList<Vertex> flags = new ArrayList<>();//TODO: Make this hashset
        File input = new File("largeCases/input/stressPart1-2.txt");
        Scanner reader = new Scanner(input);
        int vertexCount = Integer.parseInt(reader.nextLine());
        int flagCount = Integer.parseInt(reader.nextLine());
        String[] thirdLine = reader.nextLine().split(" ");
        Vertex startVertex = new Vertex(thirdLine[0], index++);
        Vertex finishVertex = new Vertex(thirdLine[1], index++);
        vertices.put(thirdLine[0], startVertex);
        vertices.put(thirdLine[1], finishVertex);
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
                Vertex v = new Vertex(s, index++);
                v.setFlag(true);
                vertices.put(s, v);
                flags.add(v);
            }
        }
        for (int i=0; i<vertexCount; i++){
            String[] line = reader.nextLine().split(" ");
            Vertex v1 = vertices.get(line[0]);
            // If vertex not in vertexList.
            if (v1 == null){
                v1 = new Vertex(line[0], index++);
                vertices.put(line[0], v1);
            }
            for (int j=1; j<line.length; j+=2){
                Vertex v2;
                if (!vertices.containsKey(line[j])){
                    v2 = new Vertex(line[j], index++);
                    vertices.put(line[j], v2);
                }
                else
                    v2 = vertices.get(line[j]);
                v1.adjAdd(new Edge(v2, Integer.parseInt(line[j+1])));
                v2.adjAdd(new Edge(v1, Integer.parseInt(line[j+1])));
            }
        }
        long cp1 = System.currentTimeMillis();
        System.out.println("reading:" + (cp1 - start));
        Simulator s = new Simulator();
        System.out.println(s.dijkstra(startVertex,finishVertex, vertexCount));
        System.out.println(s.flag(flags, flagCount, vertexCount));
        long finish = System.currentTimeMillis();
        System.out.println("total:" + (finish - start));
    }
}
