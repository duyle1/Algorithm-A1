import sun.java2d.pipe.SpanShapeRenderer;

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Adjacency list implementation for the FriendshipGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjList<T extends Object> implements FriendshipGraph<T>
{
    private Map<Node<T>, SimpleLinkedList<Node>> adjList;

    /**
     * Contructs empty graph.
     */
    public AdjList() {
        adjList = new HashMap<>();
    }

    public AdjList(InputStream ins)
    {
        this();

        Scanner input = new Scanner(ins);

        while (input.hasNextInt()) {
            Integer vertex1 = input.nextInt();
            Node node1 = new Node<>(vertex1);
            if (this.getNode((T)vertex1) == null) {
                adjList.put(node1, new SimpleLinkedList<>());
            } else {
                node1 = this.getNode((T)vertex1);
            }

            if (input.hasNextInt()) {
                Integer vertex2 = input.nextInt();
                Node node2 = new Node<>(vertex2);
                if (this.getNode((T)vertex2) == null) {
                    adjList.put(node2, new SimpleLinkedList<>());
                } else {
                    node2 = this.getNode((T)vertex2);
                }

                adjList.get(node1).add(node2);
                adjList.get(node2).add(node1);
            }
        }
    }

    private Node getNode(T vertex)
    {
        Node node = null;
        for (Node key : adjList.keySet()) {
            if (key.getVert().equals(vertex)) {
                node = key;
            }
        }
        return node;
    }

    public void addVertex(T vertLabel)
    {
       if (this.getNode(vertLabel) == null) {
           System.out.printf("Vertex %s added successfully\n", vertLabel.toString());
           adjList.put(new Node<>(vertLabel), new SimpleLinkedList<>());
       } else {
           System.err.printf("Vertex %s already exists\n", vertLabel.toString());
       }
    }


    public void addEdge(T srcLabel, T tarLabel)
    {
        Node srcNode = this.getNode(srcLabel);
        Node tarNode = this.getNode(tarLabel);
        if(srcNode != null && tarNode != null) {
            SimpleLinkedList<Node> srcEdges = adjList.get(srcNode);
            SimpleLinkedList<Node> tarEdges = adjList.get(tarNode);

            if (!srcEdges.search(tarNode) && !tarEdges.search(srcNode)) {
                System.out.printf("Edge %s-%s added successfully\n", srcLabel.toString(), tarLabel.toString());
                srcEdges.add(tarNode);
                tarEdges.add(srcNode);
            } else {
                System.err.printf("Edge %s-%s already exists", srcLabel.toString(), tarLabel.toString());
            }
        } else {
            System.err.println("One or both of the vertices do not exist in the map");
        }
    }


    public ArrayList<T> neighbours(T vertLabel)
    {
        ArrayList<T> neighbours = new ArrayList<>();

        if (this.getNode(vertLabel) !=  null) {
            SimpleLinkedList<Node> vertEdges = adjList.get(this.getNode(vertLabel));

            for (int edgesIndex = 0; edgesIndex < vertEdges.size; edgesIndex++) {
                neighbours.add((T) vertEdges.get(edgesIndex).getVert());
            }
        } else {
            System.err.printf("Vertex %s does not exist\n", vertLabel.toString());
        }

        return neighbours;
    }


    public void removeVertex(T vertLabel)
    {
        if (this.getNode(vertLabel) != null) {
            System.out.printf("vertex %s removed successfully\n", vertLabel.toString());
            adjList.remove(this.getNode(vertLabel));
            for (SimpleLinkedList<Node> edges: adjList.values()) {
                for (int edgeIndex = 0; edgeIndex < edges.size; edgeIndex++) {
                    if (edges.get(edgeIndex).getVert().equals(vertLabel)) {
                        edges.remove(edgeIndex, true);
                    }
                }
            }
        } else {
            System.err.printf("The vertex %s does not exist\n", vertLabel.toString());
        }
    }


    public void removeEdge(T srcLabel, T tarLabel)
    {
        if (this.getNode(srcLabel) != null && this.getNode(tarLabel) != null) {
            Node srcNode = this.getNode(srcLabel);
            Node tarNode = this.getNode(tarLabel);

            SimpleLinkedList<Node> srcEdges = adjList.get(srcNode);
            SimpleLinkedList<Node> tarEdges = adjList.get(tarNode);
            if (srcEdges.search(tarNode) && tarEdges.search(srcNode)) {
                System.out.printf("Edge %s-%s removed successfully\n", srcLabel.toString(), tarLabel.toString());
                srcEdges.remove(tarNode);
                tarEdges.remove(srcNode);
            } else {
                System.err.printf("Edge %s-%s does not exist", srcLabel.toString(), tarLabel.toString());
            }
        } else {
            System.err.println("One or both of the vertices do not exist");
        }
    }


    public void printVertices()
    {
        System.out.println("The list of vertices: ");

        for (Node node: adjList.keySet()) {
            System.out.println(node.getVert().toString());
        }
    }


    public void printEdges()
    {
        System.out.println("The list of edges:");

        for (Node node: adjList.keySet()) {
            SimpleLinkedList<Node> edgeList = adjList.get(node);
            System.out.printf("Vertex %s: ", node.getVert().toString());

            for (int i = 0; i < edgeList.size; i++) {
                System.out.print(edgeList.get(i).getVert().toString() + " ");
            }
            System.out.println();

        }

    }

    public int shortestPathDistance(T srcVert, T tarVert)
    {
        if (this.getNode(srcVert) != null && this.getNode(tarVert) != null) {
            for (Node node : adjList.keySet()) {
                node.setMark(false);
            }

            Queue<Node> q = new ArrayDeque<Node>();
            Node srcNode = this.getNode(srcVert);
            srcNode.setMark(true);
            q.add(srcNode);

            // while queue not empty, there are more vertices to check
            while (!q.isEmpty()) {
                Node node = q.remove();
                T vertex = (T) node.getVert();
                int dist = node.getDist();

                // found target vertex
                if (vertex.equals(tarVert)) {
                    return dist;
                }

                // else keep expanding to neighbours
                for (T vert : this.neighbours(vertex)) {
                    node = this.getNode(vert);
                    if (!node.getMark()) {
                        node.setMark(true);
                        node.setDist(dist + 1);
                        q.add(node);
                    }
                }
            }
        } else {
            System.err.println("One or both of the vertices do not exist");
        }

        return disconnectedDist;
    }

    private class Node<T>
    {
        private boolean marked;
        private T vertex;
        private int distance;

        public Node(T vertex) {
            this.vertex = vertex;
            this.distance = 0;
            this.marked = false;
        }

        public T getVert() {
            return vertex;
        }

        public int getDist() {
            return distance;
        }

        public boolean getMark() {
            return this.marked;
        }

        public void setMark(boolean value) {
            this.marked = value;
        }

        public void setDist(int dist) {
            this.distance = dist;
        }

    }

}
