import java.io.*;
import java.util.*;

public class project4 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        FileReader fr = new FileReader("smallCases/input/inp13.txt");
        BufferedReader br = new BufferedReader(fr);
        Graph graph = new Graph(); //TODO: Get rid of vertices map and remove graph class
        HashMap<String, Vertex> vertices = graph.getVertices();
        ArrayList<Vertex> flags = new ArrayList<>();//TODO: Make this hashset
        int index = 0;
        int vertexCount = Integer.parseInt(br.readLine()); // First line
        int flagCount = Integer.parseInt(br.readLine()); // Second line
        String thirdLine = br.readLine();
        StringTokenizer tk = new StringTokenizer(thirdLine);
        Vertex startVertex = new Vertex(tk.nextToken(), index++);
        vertices.put(startVertex.getName(), startVertex);
        Vertex end = new Vertex(tk.nextToken(), index++);
        vertices.put(end.getName(), end);
        // FLAGS
        tk = new StringTokenizer(br.readLine());
        String s = tk.nextToken(); // First flag
        while (s!=null){
            if (startVertex.getName().equals(s)) {
                startVertex.setFlag(true);
                flags.add(startVertex);
            } else if (end.getName().equals(s)) {
                end.setFlag(true);
                flags.add(end);
            } else {
                Vertex v = new Vertex(s, index++);
                v.setFlag(true);
                vertices.put(s, v);
                flags.add(v);
            }
            try{
                s = tk.nextToken();
            } catch (NoSuchElementException e){
                s = null;
            }
        }
        // EDGES
        String currentLine =br.readLine();
        while (currentLine != null) {
            tk = new StringTokenizer(currentLine);
            s = tk.nextToken();
            Vertex v1 = vertices.get(s);
            if (v1 == null){
                v1 = new Vertex(s, index++);
                vertices.put(s, v1);
            }
            while (s!=null){
                try{
                    s = tk.nextToken();
                    Vertex v2 = vertices.get(s);
                    if (v2 == null){
                        v2 = new Vertex(s, index++);
                        vertices.put(s, v2);
                    }
                    s = tk.nextToken();
                    v1.adjAdd(new Edge(v2, Integer.parseInt(s)));
                    v2.adjAdd(new Edge(v1, Integer.parseInt(s)));
                } catch (NoSuchElementException e){
                    s = null;
                }
            }
            currentLine = br.readLine();
        }
        long cp1 = System.currentTimeMillis();
        System.out.println("reading:" + (cp1 - start));
        Simulator sim = new Simulator();
        System.out.println(sim.dijkstra(startVertex,end, vertexCount));
        System.out.println(sim.flag(flags, flagCount, vertexCount));
        long finish = System.currentTimeMillis();
        System.out.println("total:" + (finish - start));
    }
}
