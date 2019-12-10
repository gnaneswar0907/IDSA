/** Starter code for LP2
 *  @author rbk ver 1.0
 *  @author SA ver 1.1
 */

// change to your netid
package gxg170000;

import gxg170000.Graph.Vertex;
import gxg170000.Graph.Edge;
import gxg170000.Graph.GraphAlgorithm;
import gxg170000.Graph.Factory;
import gxg170000.Graph.Timer;

import java.util.*;
import java.io.File;


public class Euler extends GraphAlgorithm<Euler.EulerVertex> {
    static int VERBOSE = 1;
    Vertex start;
    LinkedList<Vertex> tour;
    Stack<Vertex> currentPath = new Stack<>();
    HashMap<Vertex, LinkedList<Edge>> edgeCount = new HashMap<>();
    
	// You need this if you want to store something at each node
    static class EulerVertex implements Factory {
        boolean isVisited;

        EulerVertex(Vertex u) {
            this.isVisited = false;
        }
	public EulerVertex make(Vertex u) { return new EulerVertex(u); }

    }

    // To do: function to find an Euler tour
	public Euler(Graph g, Vertex start) {
	super(g, new EulerVertex(null));
	this.start = start;
	tour = new LinkedList<>();
    }

    private void dfsHelper(Vertex v, Graph gra) {
        if(!get(v).isVisited){
            get(v).isVisited = true;
            for(Edge e : gra.outEdges(v)){
                Vertex u = e.otherEnd(v);
                dfsHelper(u, gra);
            }
        }
    }

    private boolean isStronglyConnected() {
        dfsHelper(start, g);

        for(Vertex v : g.getVertexArray()){
            if(!get(v).isVisited){
                return false;
            }
        }

        g.reverseGraph();

        for(Vertex v : g.getVertexArray()){
            get(v).isVisited = false;
        }

        dfsHelper(start, g);

        for(Vertex v : g.getVertexArray()){
            if(!get(v).isVisited){
                return false;
            }
        }

        g.reverseGraph();

        return true;
    }

    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */

    public boolean isEulerian() {

        if(!isStronglyConnected()){
            System.out.println("Graph is not strongly connected");
            return false;
        }

        for(Vertex v : g.getVertexArray()){
            if(v.outDegree() != v.inDegree()){
                System.out.println("Graph is not Eulerian, beacause indegree = "+v.inDegree()+
                        " and outdegree = "+ v.outDegree()+ " at Verted "+ v.name);
                return false;
            }
        }

        return true;
	}


    public List<Vertex> findEulerTour() {
	if(!isEulerian()) {
	    return new LinkedList<Vertex>();
	}
	for(Vertex v : g.getVertexArray()){
        edgeCount.put(v, (LinkedList<Edge>) g.adj(v).outEdges);
    }

	currentPath.push(start);
	Vertex currentVertex = start;
	while(!currentPath.isEmpty()){
	    if(edgeCount.get(currentVertex).size()>0){
	        currentPath.push(currentVertex);
	        Edge ee = edgeCount.get(currentVertex).removeLast();
	        Vertex nextVertex = ee.otherEnd(currentVertex);
            currentVertex = nextVertex;
        }
	    else {
	        tour.addFirst(currentVertex);
	        currentVertex = currentPath.pop();
        }
    }

	return tour;
    }
    
    public static void main(String[] args) throws Exception {
        Scanner in;
        if (args.length > 1) {
            in = new Scanner(System.in);
        } else {
	    String input = "9 13 1 2 1 2 3 1 3 1 1 3 4 1 4 5 1 5 6 1 6 3 1 4 7 1 7 8 1 8 4 1 5 7 1 7 9 1 9 5 1";
            in = new Scanner(input);
        }
	    int start = 1;
        if(args.length > 1) {
	    start = Integer.parseInt(args[1]);
		}
		if(args.length > 2) {
            VERBOSE = Integer.parseInt(args[2]);
        }
        Graph g = Graph.readDirectedGraph(in);
	    Vertex startVertex = g.getVertex(start);
        Timer timer = new Timer();


        Euler euler = new Euler(g, startVertex);
        List<Vertex> tour = euler.findEulerTour();


            timer.end();
            if(VERBOSE > 0) {
                if(tour.size()>0){
                    System.out.println("Output:");
                    // print the tour as sequence of vertices (e.g., 3,4,6,5,2,5,1,3)
                    for(int i=0; i<tour.size();i++){
                        System.out.print(tour.get(i).name + "  ");
                    }
                }
                else {
                    System.out.println("Not Eulerian");
                }
            System.out.println();
            }
            System.out.println(timer);


        }

        public void setVerbose(int ver) {
        VERBOSE = ver;
    }
}