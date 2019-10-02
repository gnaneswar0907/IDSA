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

import java.util.Iterator;
import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;


public class Euler extends GraphAlgorithm<Euler.EulerVertex> {
    static int VERBOSE = 1;
    Vertex start;
    List<Vertex> tour;
    Vertex temp;
    
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
	this.temp = null;
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

    private Graph transpose(Graph g) {
        Graph gg = new Graph(g.size(), true);

        for(Vertex v : g.getVertexArray()){
            for(Edge e : g.outEdges(v)){
                Vertex u = e.otherEnd(v);
                gg.addEdge(u.name-1,v.name-1, 0);
            }
        }

        return gg;
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
            return false;
        }

        for(Vertex v : g.getVertexArray()){
            if(g.outEdges(v)!=g.inEdges(v)){
                this.temp = v;
                return false;
            }
        }

        return true;
	}


    public List<Vertex> findEulerTour() {
	if(!isEulerian()) { return new LinkedList<Vertex>(); }
       // Graph is Eulerian...find the tour and return tour
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
            if(tour!=null){
                System.out.println("Output:");
                // print the tour as sequence of vertices (e.g., 3,4,6,5,2,5,1,3)
            }
            else {
//                System.out.println("Graph is not Eulerian, beacause indegree = "+g.inEdges(euler.temp)+
//                                    " and outdegree = "+ g.outEdges(euler.temp)+ " at Verted "+ g.getVertex(euler.temp).name);
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