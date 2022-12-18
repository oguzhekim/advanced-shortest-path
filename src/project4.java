import java.io.*;
import java.util.*;

public class project4 {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);
        Graph graph = new Graph();
        HashMap<String, Vertex> vertices = graph.getVertices();
        ArrayList<Vertex> flags = new ArrayList<>();
        int index = 0;
        int vertexCount = Integer.parseInt(br.readLine()); // First line
        int flagCount = Integer.parseInt(br.readLine()); // Second line
        String[] thirdLine = br.readLine().split(" ");
        Vertex start = new Vertex(thirdLine[0], index++);
        vertices.put(thirdLine[0], start);
        Vertex end = new Vertex(thirdLine[1], index++);
        vertices.put(thirdLine[1], end);
        // FLAGS
        String[] fourthLine = br.readLine().split(" ");
        for (String s: fourthLine){
            if (start.getName().equals(s)) {
                start.setFlag(true);
                flags.add(start);
            } else if (end.getName().equals(s)) {
                end.setFlag(true);
                flags.add(end);
            } else {
                Vertex v = new Vertex(s, index++);
                v.setFlag(true);
                vertices.put(s, v);
                flags.add(v);
            }
        }
        // EDGES
        Vertex v1;
        Vertex v2;
        String currentLine = br.readLine();
        while (currentLine != null) {
            String[] arr = currentLine.split(" ");
            v1 = vertices.get(arr[0]);
            if (v1 == null){
                v1 = new Vertex(arr[0], index++);
                vertices.put(arr[0], v1);
            }
            for (int i=1; i<arr.length; i+=2){
                String s = arr[i];
                int weight = Integer.parseInt(arr[i+1]);
                v2 = vertices.get(s);
                if (v2 == null){
                    v2 = new Vertex(s, index++);
                    vertices.put(s, v2);
                }
                v1.adjAdd(new Edge(v2, weight));
                v2.adjAdd(new Edge(v1, weight));
            }
            currentLine = br.readLine();
        }
        br.close();
        fr.close();
        PrintStream ps = new PrintStream(args[1]);
        PrintStream console = System.out;
        System.setOut(ps);
        Simulator sim = new Simulator();
        System.out.println(sim.dijkstra(start, end, vertexCount));
        System.out.println(sim.flag(flags, flagCount, vertexCount));
        System.setOut(console);
    }
}
