import java.util.ArrayList;
public class Vertex {
    private final ArrayList<Edge> adjList;
    private final String name;
    private boolean flag;
    public int index;

    public Vertex(String name, int index) {
        this.adjList = new ArrayList<>();
        this.name = name;
        this.index =index;
    }
    public void adjAdd(Edge e){
        adjList.add(e);
    }
    public ArrayList<Edge> getAdjList() {
        return adjList;
    }
    public String getName() {
        return name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
