// Starter code for Postman Tour

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
import java.util.LinkedList;

// Find a minimum weight postman tour that goes through every edge of g at least once

public class Postman {
    MinCostFlow mcf;
    Graph g;

    public Postman(Graph g) {
        this.g = g;
        HashMap<Edge, Integer> capacity = new HashMap<>();
        HashMap<Edge, Integer> cost = new HashMap<>();

        Graph new_graph = new Graph(g.n + 2, true);
        int i = 1;
        Edge temp_e;
        for (Vertex u : g) {
            for (Edge e : g.adj(u).outEdges) {
                Vertex v = e.otherEnd(u);
                temp_e = new_graph.addEdge(new_graph.getVertex(u), new_graph.getVertex(v), e.getWeight(), e.getName());
                capacity.put(temp_e, Integer.MAX_VALUE);
                cost.put(temp_e, temp_e.getWeight());
                i++;
            }
        }
        for (Vertex u : g) {
            int excess = u.inDegree() - u.outDegree();
            if (excess > 0) {
                temp_e = new_graph.addEdge(new_graph.getVertex(g.n + 1), new_graph.getVertex(u), 0, i);
                capacity.put(temp_e, excess);
                cost.put(temp_e, 0);
            } else if (excess < 0) {
                temp_e = new_graph.addEdge(new_graph.getVertex(u), new_graph.getVertex(g.n + 2), 0, i);
                capacity.put(temp_e, -excess);
                cost.put(temp_e, 0);
            }
            i++;
        }

        this.mcf = new MinCostFlow(new_graph, new_graph.getVertex(g.n + 1), new_graph.getVertex(g.n + 2), capacity, cost);
        this.mcf.costScalingMinCostFlow(0);
    }

    public Postman(Graph g, Vertex startVertex, Vertex endVertex) {
    }

    // Get a postman tour
    public List<Edge> getTour() {
        List<Edge> Tour = new LinkedList<>();
        Map<Edge, Integer> flow = this.mcf.flow;
        for (Edge e : flow.keySet()) {
            for (int i = 0; i < flow.get(e); i++) {
                g.addEdge(e.from, e.to, e.weight, e.name);
            }
        }
        Vertex startVertex = g.getVertex(1);
        Euler euler = new Euler(g, startVertex);
        List<Vertex> tour = euler.findEulerTour();
        for (int i = 0; i < tour.size() - 1; i++) {
            Vertex u = tour.get(i);
            for (Edge e : g.adj(u).outEdges) {
                Vertex v = e.toVertex();
                if (v.equals(tour.get(i + 1))) {
                    Tour.add(e);
                }
            }
        }
        return Tour;
    }

    // Find length of postman tour
    public long postmanTour() {
        long length = 0;
        for (Vertex u : g) {
            for (Edge e : g.adj(u).outEdges) {
                length += e.getWeight();
            }
        }
        return length + mcf.minCost;
    }
}
