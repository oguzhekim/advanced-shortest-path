import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Reader {
    private final ArrayList<Vertex> vertexList = new ArrayList<>();
    public ArrayList<Vertex> read(String dir) throws FileNotFoundException {
        File input = new File(dir);
        Scanner reader = new Scanner(input);
        int vertexCount = Integer.parseInt(reader.nextLine());
        int flagCount = Integer.parseInt(reader.nextLine());
        String[] thirdLine = reader.nextLine().split(" ");
        Vertex startVertex = new Vertex(thirdLine[0]);
        Vertex finishVertex = new Vertex(thirdLine[1]);
        startVertex.setStart(true);
        finishVertex.setFinish(true);
//        startVertex.adjAdd(new Edge(startVertex, 0));
//        finishVertex.adjAdd(new Edge(finishVertex, 0));
        vertexList.add(startVertex);
        vertexList.add(finishVertex);
        String[] fourthLine = reader.nextLine().split(" ");
        // Create vertices with flags
        for (String s : fourthLine) {
            if (Objects.equals(s, startVertex.getName())) {
                startVertex.setFlag(true);
            } else if (Objects.equals(s, finishVertex.getName())) {
                finishVertex.setFlag(true);
            } else {
                Vertex v = new Vertex(s);
                v.setFlag(true);
//                v.adjAdd(new Edge(v, 0));
                vertexList.add(v);
            }
        }
        while (reader.hasNextLine()){
            String[] line = reader.nextLine().split(" ");
            Vertex v1 = findVertex(line[0]);
            // If vertex not in vertexList.
            if (v1 == null){
                v1 = new Vertex(line[0]);
                // Distance of vertex to itself is 0.
//                v1.adjAdd(new Edge(v1, 0));
            }
            for (int i=1; i<line.length; i+=2){
                Vertex v2 = findVertex(line[i]);
                if (v2 == null){
                    v2 = new Vertex(line[i]);
//                    v2.adjAdd(new Edge(v2, 0));
                    vertexList.add(v2);
                }
                v1.adjAdd(new Edge(v2, Integer.parseInt(line[i+1])));
                v2.adjAdd(new Edge(v1, Integer.parseInt(line[i+1])));
            }
        }
        return vertexList;
    }
    private Vertex findVertex(String name) {
        for(Vertex v : vertexList) {
            if(v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }
}
