import java.util.ArrayList;
public class Vertex {
    private final ArrayList<Edge> adjList;
    private final String name;
    private boolean flag, start, finish;
    private int shortestPath;
    public int index;
    public int minVal;

    public Vertex(String name, int index) {
        this.adjList = new ArrayList<>();
        this.name = name;
        this.shortestPath = Integer.MAX_VALUE;
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

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(int shortestPath) {
        this.shortestPath = shortestPath;
    }

    public int getMinVal() {
        return minVal;
    }
}
