// Starter code for mincost flow

/**
 * @author Meetika Sharma - mxs173530
 * @author Ch Muhammad Talal Muneer - cxm180004
 * @author Chirag Shahi - cxs180005
 * @author Gnaneswar Gandu - gxg170000
 */

package gxg170000;
import gxg170000.Graph.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedList;


// Inital flow should be max flow
public class MinCostFlow {
    Vertex s, t;
    HashMap<Edge, Integer> capacity;
    HashMap<Edge, Integer> cost;
    HashMap<Edge, Integer> flow;
    Graph g;
    List<Vertex> lst;
    Iterator<Vertex> it;
    int Epsilon, maxflow, minCost;
    int excess[];
    int price[];

    // Constructor to store source, sink, graph and the capacity of edges
    public MinCostFlow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
        this.g = g;
        this.s = s;
        this.t = t;
        this.capacity = capacity;
        this.cost = cost;
        flow = new HashMap<>();
        lst = new LinkedList<>();
        excess = new int[g.n];
        price = new int[g.n];
        Epsilon = Integer.MIN_VALUE;
        minCost = 0;
    }


    // Return cost of d units of flow found by cost scaling algorithm
    int costScalingMinCostFlow(int d) {
        initialize();
        while (Epsilon > 0) {
            refine();
            Epsilon /= 2;
        }
        for(Map.Entry<Edge, Integer> entry: flow.entrySet()) {
            minCost += entry.getValue() * cost(entry.getKey());
        }
        return minCost;
    }

    private void refine() {
        for (Vertex u : g) {
            for (Edge e : g.adj(u).outEdges) {
                Vertex v = e.toVertex();
                if(RC(e,u,v) < 0) {
                    int additionalFlow = capacity(e) - flow(e);
                    flow.put(e, capacity(e));
                    excess[u.getIndex()] -= additionalFlow;
                    excess[v.getIndex()] += additionalFlow;
                }
                else {
                    excess[u.getIndex()] += flow(e);
                    excess[v.getIndex()] -= flow(e);
                    flow.put(e, 0);
                }
            }
        }
        boolean done = false;
        while(!done) {
            it = lst.iterator();
            done = true;
            while(it.hasNext()) {
                Vertex u = it.next();
                if(excess[u.getIndex()] == 0)
                    continue;
                int oldPrice = price[u.getIndex()];
                discharge(u);
                if(oldPrice != price[u.getIndex()]) {
                    done = false;
                    it.remove();
                    lst.add(0, u);
                    break;
                }
            }
        }
    }

    // This function pushes the excess out of a given vertex
    private void discharge(Vertex u) {
        while(excess[u.getIndex()] > 0) {
            // Checking for forward edges in residual graph
            for(Edge e : g.adj(u).outEdges) {
                Vertex v = e.otherEnd(u);
                if(inResidualGraph(e, u) && RC(e,u,v) < 0) {
                    push(e,u,v);
                }
                if(excess[u.getIndex()] == 0)
                    return;
            }
            // Checking for reverse edges in residual graph
            for(Edge e : g.adj(u).inEdges) {
                Vertex v = e.fromVertex();
                if(inResidualGraph(e,u) && RC(e,u,v) < 0) {
                    push(e,u,v);
                }
                if(excess[u.getIndex()] == 0)
                    return;
            }
            relabel(u);
        }
    }

    // This functions sends flow from u along the edge
    private void push(Edge e, Vertex u, Vertex v) {
        int delta = 0;
        if(e.fromVertex().equals(u)) {
            delta = Math.min((capacity(e) - flow(e)), excess[u.getIndex()]);
            flow.put(e, flow(e) + delta);
        }
        else {
            delta = Math.min(flow(e), excess[u.getIndex()]);
            flow.put(e, flow(e) - delta);
        }
        excess[u.getIndex()] -= delta;
        excess[v.getIndex()] += delta;
    }

    // This function reduces the price associated with production at vertex u
    private void relabel(Vertex u) {
        price[u.getIndex()] = price[u.getIndex()] - Epsilon;
    }

    // This function calculates the reduced cost of a flow through edge e
    private int RC(Edge e, Vertex u, Vertex v) {
        if(e.fromVertex().equals(u))
            return cost(e) + price[u.getIndex()] - price[v.getIndex()];
        else
            return price[u.getIndex()] - price[v.getIndex()] - cost(e);
    }

    // This function initializes the flow along edges in case of maxflow, excess and prices at each node
    private void initialize() {
        for(Vertex u : g) {
            price[u.getIndex()] = 0;
            excess[u.getIndex()] = 0;
            lst.add(u);
            for(Edge e: g.adj(u).outEdges) {
                flow.put(e, 0);
            }
        }

        Flow f = new Flow(g,s,t,capacity);
        maxflow = f.preflowPush();

        excess[s.getIndex()] = maxflow;
        excess[t.getIndex()] = -maxflow;

        for (Integer value : cost.values()) {
            if (value > Epsilon)
                Epsilon = value;
        }
    }

    // flow going through edge e
    public int flow(Edge e) {
        return flow.containsKey(e) ? flow.get(e) : 0;
    }

    // This function returns whether the edge e exists out of vertex u in the residual graph
    private boolean inResidualGraph(Edge e, Vertex u) {
        if (e.fromVertex().equals(u))
            return flow(e) < capacity(e);
        return flow(e) > 0;
    }


    // capacity of edge e
    public int capacity(Edge e) {
        return capacity.get(e);
    }

    // cost of edge e
    public int cost(Edge e) {
        return cost.get(e);
    }

    // This function returns the value of maximum flow through the graph
    public int getMaxflow() {
        return maxflow;
    }
}
