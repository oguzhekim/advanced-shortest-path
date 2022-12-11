import java.io.FileNotFoundException;
import java.util.ArrayList;

public class project4 {
    public static void main(String[] args) throws FileNotFoundException {
        Reader reader = new Reader();
        ArrayList<Vertex> vertexList = reader.read("smallCases/smallCases/input/inp12.txt");
        RaceDaySimulation s = new RaceDaySimulation();
        System.out.println(s.simulate(vertexList));
    }
}
