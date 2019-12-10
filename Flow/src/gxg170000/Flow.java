// Starter code for max flow

/**
 * @author Meetika Sharma - mxs173530
 * @author Ch Muhammad Talal Muneer - cxm180004
 * @author Chirag Shahi - cxs180005
 * @author Gnaneswar Gandu - gxg170000
 */


package gxg170000;
import gxg170000.Graph.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;


public class Flow extends GraphAlgorithm<Flow.FlowVertex>{
    Vertex s;
    Vertex t;
    HashMap<Edge, Integer> capacity;
    HashMap<Edge, Integer> flowmap = new HashMap<>();

    public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
        super(g, new FlowVertex((Vertex) null));
        this.s = s;
        this.t = t;
        this.capacity = capacity;
    }

    public static class FlowVertex implements Factory{
        int height;
        int excess;
        Vertex node;

        FlowVertex(Vertex u) {
            node = u;
            height =  0;
            excess = 0;
        }
        public FlowVertex make(Vertex u) { return new FlowVertex(u); }
    }

    public void initialize() {
        for(Vertex u : g.getVertexArray()){
            get(u).height = 0;
            get(u).excess = 0;
        }

        for (Edge e: g.getEdgeArray()){
            flowmap.put(e, 0);
        }

        get(s).height = g.getVertexArray().length;

        for (Edge e : g.incident(s)){
            flowmap.put(e, capacity.get(e));
            get(s).excess = get(s).excess - capacity.get(e);
            get(e.otherEnd(s)).excess = get(e.otherEnd(s)).excess + capacity.get(e);
        }

    }

    public void relabelToFront(){
        initialize();


        LinkedList<Vertex> L = new LinkedList<>();

        for(Vertex u: g.getVertexArray()){
            if(u != s && u != t){
                L.addFirst(u);
            }
        }

        boolean done = false;

        while(!done){
            System.out.println("main loop");
            Iterator<Vertex> it = L.iterator();
            done = true;
            Vertex u = null;

            while(it.hasNext()){
                System.out.println("sub loop start");
                u = it.next();

                if(get(u).excess == 0){
                    continue;
                }

                int oldh = get(u).height;
                System.out.println("discharge loop start " );
                discharge(u);
                System.out.println("discharge loop end " );


                if(get(u).height != oldh){
                    done = false;
                    break;
                }
                System.out.println("sub loop end");
            }

            if(!done){
                L.remove(u);
                L.addFirst(u);
            }
            System.out.println("main loop end");

        }


    }

    public void discharge(Vertex vertex){
        while(get(vertex).excess > 0){
            System.out.println("Inside discharge start " + vertex.name);
            for (Edge e: g.incident(vertex)){

                Vertex v = e.otherEnd(vertex);
                int h = 1 + get(v).height;
                System.out.println("Inside discharge --------  " + inGf(vertex, e) + " " + h + " " + get(vertex).height );
                if(inGf(vertex, e) && (get(vertex).height == h)){
                    System.out.println("******* ");
                    push(vertex, v, e);
                    if(get(vertex).excess == 0){
                        return;
                    }
                }
            }
            System.out.println("Inside discharge end " );
            relabel(vertex);
        }
    }

    public void relabel(Vertex u){

        int minh = Integer.MAX_VALUE;
        for(Edge e : g.incident(u)){
            minh = Math.min(minh, get(e.otherEnd(u)).height);
        }

        get(u).height =  1 + minh;

    }

    public boolean inGf(Vertex u, Edge e){
        if(e.from == u){
            if(flowmap.get(e) <= capacity.get(e)){
                return true;
            }
            else{
                System.out.println("This False " + flowmap.get(e) + " " + capacity.get(e) + " " + e.name);
                return false;
            }
        }
        else{
            if(flowmap.get(e) > 0){
                return true;
            }
            else{
                System.out.println("That False " + flowmap.get(e) + " " + capacity.get(e) + " " + e.name);
                return false;
            }
        }
        // return e.from == u ? flowmap.get(e) < capacity.get(e) : flowmap.get(e) > 0;
    }


    public void push(Vertex u, Vertex v, Edge e ){
        int delta = Math.min(get(u).excess, capacity.get(e) - flowmap.get(e));

        if(e.from == u){
            flowmap.put(e, flowmap.get(e) + delta);
        }
        else{
            flowmap.put(e, flowmap.get(e) - delta);
        }

        get(u).excess -= delta;
        get(v).excess += delta;
    }

    // Return max flow found. Use either relabel to front or FIFO.
    public int preflowPush() {
        relabelToFront();
        return -1*get(s).excess;
    }

    // flow going through edge e
    public int flow(Edge e) {
        return flowmap.get(e);
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return capacity.get(e);
    }

    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
        return null;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
        return null;
    }
}