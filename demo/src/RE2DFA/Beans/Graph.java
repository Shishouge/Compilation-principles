package RE2DFA.Beans;


import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private Node start;
    private Node end;

    public Graph() {
        edges = new ArrayList<Edge>();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public void reset() {
        Node.reset();
    }

    public void Star(Object obj) {
        if (obj.getClass().getName().equals(Graph.class.getName())) {
            addStar((Graph) obj);
            return;
        }
        if (obj.getClass().getName().equals(String.class.getName())) {
            addStar((String) obj);
            return;
        } else {
            throw new RuntimeException("You have an error in your Regex syntax");
        }
    }

    public void Union(Object obj1, Object obj2) {
        if (obj1.getClass().getName().equals(String.class.getName())) {
            if (obj2.getClass().getName().equals(Graph.class.getName())) {
                addUnion((String) obj1, (Graph) obj2);
                return;
            }
            if (obj2.getClass().getName().equals(String.class.getName())) {
                addUnion((String) obj1, (String) obj2);
                return;
            }
        }
        if (obj1.getClass().getName().equals(Graph.class.getName())) {
            if (obj2.getClass().getName().equals(Graph.class.getName())) {
                addUnion((Graph) obj1, (Graph) obj2);
                return;
            }
            if (obj2.getClass().getName().equals(String.class.getName())) {
                addUnion((Graph) obj1, (String) obj2);
                return;
            }
        } else {
            throw new RuntimeException("You have an error in your Regex syntax");
        }
    }

    public void Concat(Object obj1, Object obj2) {
        if (obj1.getClass().getName().equals(String.class.getName())) {
            if (obj2.getClass().getName().equals(Graph.class.getName())) {
                addConcat((String) obj1, (Graph) obj2);
                return;
            }
            if (obj2.getClass().getName().equals(String.class.getName())) {
                addConcat((String) obj1, (String) obj2);
                return;
            }
        }
        if (obj1.getClass().getName().equals(Graph.class.getName())) {
            if (obj2.getClass().getName().equals(Graph.class.getName())) {
                addConcat((Graph) obj1, (Graph) obj2);
                return;
            }
            if (obj2.getClass().getName().equals(String.class.getName())) {
                addConcat((Graph) obj1, (String) obj2);
                return;
            }
        } else {
            throw new RuntimeException("You have an error in your Regex syntax");
        }
    }

    public void addStar(Graph graph) {
        Node begNode = new Node();
        Node endNode = new Node();
        Edge edge1 = new Edge(begNode,endNode,"epsilon");
        Edge edge2 = new Edge(graph.getEnd(), endNode, "epsilon");
        Edge edge3 = new Edge(begNode, graph.getStart(), "epsilon");
        Edge edge4 = new Edge(graph.getEnd(),graph.getStart(),"epsilon");
        for (int i = 0; i < graph.getEdges().size(); i++) {
            this.edges.add(graph.getEdges().get(i));
        }
        this.edges.add(edge1);
        this.edges.add(edge2);
        this.edges.add(edge3);
        this.edges.add(edge4);
        this.start = begNode;
        this.end = endNode;
    }

    public void addStar(String character) {
        Node center = new Node();
        Node beg = new Node();
        Node end = new Node();
        Edge edge1 = new Edge(center, center, character);
        Edge edge2 = new Edge(beg, center, "epsilon");
        Edge edge3 = new Edge(center, end, "epsilon");
        this.edges.add(edge1);
        this.edges.add(edge2);
        this.edges.add(edge3);
        this.start = beg;
        this.end = end;
    }

    public void addUnion(String character, Graph graph) {
        Node begNode = new Node();
        Node endNode = new Node();
        Edge edge1 = new Edge(begNode, graph.getStart(), "epsilon");
        Edge edge2 = new Edge(graph.getEnd(), endNode, "epsilon");
        Edge edge3 = new Edge(begNode, endNode, character.toString());
        for (int i = 0; i < graph.getEdges().size(); i++) {
            this.edges.add(graph.getEdges().get(i));
        }
        this.edges.add(edge1);
        this.edges.add(edge2);
        this.edges.add(edge3);
        this.start = begNode;
        this.end = endNode;
    }

    public void addUnion(Graph graph, String character) {
        Node begNode = new Node();
        Node endNode = new Node();
        Edge edge1 = new Edge(begNode, graph.getStart(), "epsilon");
        Edge edge2 = new Edge(graph.getEnd(), endNode, "epsilon");
        Edge edge3 = new Edge(begNode, endNode, character.toString());
        for (int i = 0; i < graph.getEdges().size(); i++) {
            this.edges.add(graph.getEdges().get(i));
        }
        this.edges.add(edge1);
        this.edges.add(edge2);
        this.edges.add(edge3);
        this.start = begNode;
        this.end = endNode;
    }

    public void addUnion(Graph graph1, Graph graph2) {
        Node begNode = new Node();
        Node endNode = new Node();
        Edge edge1 = new Edge(begNode, graph1.getStart(), "epsilon");
        Edge edge2 = new Edge(begNode, graph2.getStart(), "epsilon");
        Edge edge3 = new Edge(graph1.getEnd(), endNode, "epsilon");
        Edge edge4 = new Edge(graph2.getEnd(), endNode, "epsilon");
        this.start = begNode;
        this.end = endNode;
        for (int i = 0; i < graph1.getEdges().size(); i++) {
            this.edges.add(graph1.getEdges().get(i));
        }
        for (int i = 0; i < graph2.getEdges().size(); i++) {
            this.edges.add(graph2.getEdges().get(i));
        }
        this.edges.add(edge1);
        this.edges.add(edge2);
        this.edges.add(edge3);
        this.edges.add(edge4);
    }

    public void addUnion(String characterOne, String characterTwo) {
        Node nodebeg = new Node();
        Node nodeend = new Node();
        Edge edgeOne = new Edge(nodebeg, nodeend, characterOne.toString());
        Edge edgeTwo = new Edge(nodebeg, nodeend, characterTwo.toString());
        edges.add(edgeOne);
        edges.add(edgeTwo);
        start = nodebeg;
        end = nodeend;
    }

    public void addConcat(String character, Graph graph) {
        Node begNode = new Node();
        Edge edge = new Edge(begNode, graph.getStart(), character);
        for (int i = 0; i < graph.getEdges().size(); i++) {
            this.edges.add(graph.getEdges().get(i));
        }
        this.edges.add(edge);
        this.start = begNode;
        this.end = graph.getEnd();
    }

    public void addConcat(Graph graph, String character) {
        Node endNode = new Node();
        Edge edge = new Edge(graph.getEnd(), endNode, character.toString());
        for (int i = 0; i < graph.getEdges().size(); i++) {
            this.edges.add(graph.getEdges().get(i));
        }
        this.edges.add(edge);
        this.start = graph.getStart();
        this.end = endNode;
    }

    public void addConcat(Graph graph1, Graph graph2) {
        Edge edge = new Edge(graph1.getEnd(), graph2.getStart(), "epsilon");
        this.start = graph1.getStart();
        this.end = graph2.getEnd();
        for (int i = 0; i < graph1.getEdges().size(); i++) {
            this.edges.add(graph1.getEdges().get(i));
        }
        for (int i = 0; i < graph2.getEdges().size(); i++) {
            this.edges.add(graph2.getEdges().get(i));
        }
        this.edges.add(edge);
    }

    public void addConcat(String characterOne, String characterTwo) {
        Node begNode = new Node();
        Node midNode = new Node();
        Node endNode = new Node();
        Edge edge1 = new Edge(begNode, midNode, characterOne.toString());
        Edge edge2 = new Edge(midNode, endNode, characterTwo.toString());
        this.start = begNode;
        this.end = endNode;
        this.edges.add(edge1);
        this.edges.add(edge2);
    }

    @Override
    public String toString() {
        String printString = "Start=" + this.start + "  End=" + this.end + "\n";
        for (int i = 0; i < edges.size(); i++) {
            printString += edges.get(i) + "\n";
        }
        return printString;
    }
}