// Starter code for SP9

package gxg170000;

import gxg170000.Graph.Vertex;
import gxg170000.Graph.Edge;
import gxg170000.Graph.GraphAlgorithm;
import gxg170000.Graph.Factory;
import gxg170000.Graph.Timer;

import gxg170000.BinaryHeap.Index;
import gxg170000.BinaryHeap.IndexedHeap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;  // weight of MST
    List<Edge> mst;    // stores edges in MST
    
    MST(Graph g) {
		super(g, new MSTVertex((Vertex) null));
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
    	boolean seen; // flag to check if the vertex is visited or not
    	Vertex parent; // to keep track of the vertex through which current vertex was found
		int d; // to save the distance
		int index; //for the index in indexed heap
		Vertex origVertex; // reference to the original Vertex
		Edge incidentEdge; // Edge reaching out to this MSTVertex

		// Constructing MSTVertex out of a Vertex
		MSTVertex(Vertex u) {
			this.seen = false;
			this.parent = null;
			this.d = Integer.MAX_VALUE; // setting the distance to max value
			this.index = 0;
			this.origVertex = u;
			this.incidentEdge = null;
		}

		MSTVertex(MSTVertex u) {  // for prim2
		}

		public MSTVertex make(Vertex u) { return new MSTVertex(u); }

		public void putIndex(int index) {
			this.index = index;
		}

		public int getIndex() { return this.index; }

		//Ordering MSTVertices on the distance attribute.
		public int compareTo(MSTVertex other) {
			if(other == null || this.d > other.d) return 1;
			else if(this.d<other.d) return -1;
			else return 0;
		}
    }

    public long kruskal() {
	algorithm = "Kruskal";
	Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        return wmst;
    }

	// Prim's MST Algorithm - Take 3: using Indexed Binary Heap
    public long prim3(Vertex s) {
		algorithm = "Prim using indexed heap";
		mst = new LinkedList<>();
		wmst = 0;
		IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
		// Initialization
		for(Vertex v:g.getVertexArray()){
			get(v).seen = false;
			get(v).parent = null;
			get(v).d = Integer.MAX_VALUE;
			get(v).putIndex(v.getIndex());
		}
		get(s).d = 0;

		// Indexed Heap q will always have all the vertices almost all the time.
		for(Vertex v:g.getVertexArray()) q.add(get(v));

		while(!q.isEmpty()){
			MSTVertex u = q.remove();  // returning the top index from queue
			u.seen = true;
			wmst = wmst + u.d;

			// Adding edge to the MST
			if(u.parent !=null) mst.add(u.incidentEdge);

			for(Edge e: g.incident(u.origVertex)){
				Vertex v = e.otherEnd(u.origVertex);
				if(!get(v).seen && (e.getWeight()<get(v).d)){
					get(v).d = e.getWeight();
					get(v).parent = u.origVertex;
					get(v).incidentEdge = e; // edge reaching out to MSTVertex get(v)
					q.decreaseKey(get(v)); // percolateUp(MSTVertex v), if needed
				}
			}
		}
		return wmst;
    }

    public long prim2(Vertex s) {
	algorithm = "PriorityQueue<Vertex>";
        mst = new LinkedList<>();
	wmst = 0;
	PriorityQueue<MSTVertex> q = new PriorityQueue<>();
	return wmst;
    }

    public long prim1(Vertex s) {
	algorithm = "PriorityQueue<Edge>";
        mst = new LinkedList<>();
	wmst = 0;
	PriorityQueue<Edge> q = new PriorityQueue<>();
	return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
	MST m = new MST(g);
	switch(choice) {
	case 0:
	    m.kruskal();
	    break;
	case 1:
	    m.prim1(s);
	    break;
	case 2:
	    m.prim2(s);
	    break;
	case 3:
	    m.prim3(s);
	    break;
	default:
	    // Boruvka to be implemented next
	    break;
	}
	return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	int choice = 3;  // prim3
        if (args.length == 0 || args[0].equals("-")) {
            in = new Scanner(System.in);
        } else {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        }

	if (args.length > 1) { choice = Integer.parseInt(args[1]); }

	Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);

	Timer timer = new Timer();
	MST m = mst(g, s, choice);
	System.out.println(m.algorithm + "\n" + m.wmst);
	System.out.println(timer.end());
    }
}