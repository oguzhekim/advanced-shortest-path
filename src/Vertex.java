import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Vertex {
    private final Queue<Edge> adjList;
    private final String name;
    private boolean flag, start, finish, visited;

    public Vertex(String name) {
        this.adjList = new PriorityQueue<>(new EdgeComparator());
        this.name = name;
    }
    public void adjAdd(Edge e){
        adjList.add(e);
    }

    public Queue<Edge> getAdjList() {
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
