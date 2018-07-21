import java.io.InputStream;
import java.util.*;


/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix<T extends Object> implements FriendshipGraph<T>
{
    private int vertNum;
    private boolean[][] adjMatrix;
    private ArrayList<Object> vertices;

    /**
     * Contructs empty graph.
     */
    public AdjMatrix()
    {
        vertices = new ArrayList<>();
    }

    public AdjMatrix(int vertNum)
    {
        this();
        this.vertNum = vertNum;
        this.adjMatrix = new boolean[vertNum][vertNum];
    }

    public AdjMatrix(InputStream ins, int vertNum)
    {
        this(vertNum);

        Scanner input = new Scanner(ins);
        while (input.hasNextInt()) {
            Object vertex1 = input.nextInt();
            if (vertices.indexOf(vertex1) < 0) {
                vertices.add(vertex1);
            }
            int index1 = vertices.indexOf(vertex1);
            adjMatrix[index1][index1] = true;

            if (input.hasNextInt()) {
                Object vertex2 = input.nextInt();
                if (vertices.indexOf(vertex2) < 0) {
                    vertices.add(vertex2);
                }
                int index2 = vertices.indexOf(vertex2);
                adjMatrix[index2][index2] = true;

                adjMatrix[index1][index2] = true;
                adjMatrix[index2][index1] = true;
            }
        }
    }


    public void addVertex(T vertLabel) {
        if (vertices.size() < vertNum) {
            int index = vertices.indexOf(vertLabel);
            if (index < 0) {
                vertices.add(vertLabel);
                index = vertices.indexOf(vertLabel);
                adjMatrix[index][index] = true;
                System.out.printf("Vertex %s added successfully\n", vertLabel.toString());
            } else if (index >= 0 && !adjMatrix[index][index]) {
                adjMatrix[index][index] = true;
                System.out.printf("Vertex %s added successfully\n", vertLabel.toString());
            } else {
                System.err.printf("Vertex %s already exists\n", vertLabel.toString());
            }
        } else {
            System.err.println("The number of vertices has exceeded the limitation");
        }
    }


    public void addEdge(T srcLabel, T tarLabel)
    {
        int srcIndex = vertices.indexOf(srcLabel);
        int tarIndex = vertices.indexOf(tarLabel);
        if (srcIndex >= 0 && tarIndex >= 0 && adjMatrix[srcIndex][srcIndex] && adjMatrix[tarIndex][tarIndex]) {
            if (!adjMatrix[srcIndex][tarIndex] && !adjMatrix[tarIndex][srcIndex]) {
                adjMatrix[srcIndex][tarIndex] = true;
                adjMatrix[tarIndex][srcIndex] = true;
                System.out.printf("Edges %s-%s added successfully\n", srcLabel.toString(), tarLabel.toString());
            } else {
                System.err.printf("The edges %s-%s already exists\n", srcLabel.toString(), tarLabel.toString());
            }
        } else {
            System.err.println("One or both of the vertices do not exist");
        }
    }


    public ArrayList<T> neighbours(T vertex)
    {
        if (vertices.indexOf(vertex) < 0) {
            System.err.printf("The vertex %s does not exist\n", vertex.toString());
            return null;
        }

        int index = vertices.indexOf(vertex);
        if (adjMatrix[index][index] = true) {
            ArrayList<T> neighbours = new ArrayList<>();
            for (int i = 0; i < this.vertNum; i++) {
                if (adjMatrix[index][i] && index != i) {
                    neighbours.add((T) vertices.get(i));
                }
            }
            return neighbours;
        } else {
            System.err.printf("The vertex %s does not exist\n", vertex.toString());
            return null;
        }
    }


    public void removeVertex(T vertLabel)
    {
        if (vertices.indexOf(vertLabel) >= 0) {
            int index = vertices.indexOf(vertLabel);
            for (int i = 0; i < this.vertNum; i++) {
                adjMatrix[i][index] = false;
                adjMatrix[index][i] = false;
            }
            System.out.printf("Vertex %s removed successfully\n", vertLabel.toString());
        } else {
            System.err.printf("Vertex %s does not exist\n", vertLabel.toString());
        }
    }


    public void removeEdge(T srcLabel, T tarLabel)
    {
        int srcIndex = vertices.indexOf(srcLabel);
        int tarIndex = vertices.indexOf(tarLabel);
        if (srcIndex >= 0 && tarIndex >= 0) {
            if (adjMatrix[srcIndex][tarIndex] && adjMatrix[tarIndex][srcIndex]) {
                adjMatrix[srcIndex][tarIndex] = false;
                adjMatrix[tarIndex][srcIndex] = false;
                System.out.printf("Edges %s-%s removeed successfully\n", srcLabel.toString(), tarLabel.toString());
            } else {
                System.err.printf("The edges %s-%s does not exist\n", srcLabel.toString(), tarLabel.toString());
            }
        } else {
            System.err.println("One or both of the vertices do not exist");
        }
    }


    public void printVertices()
    {
        System.out.println("The list of vertices");
        for (Object vertex: vertices) {
            System.out.println(vertex.toString());
        }
    }


    public void printEdges()
    {
        System.out.println("The list of edges:");

        for (int i = 0; i < vertNum; i++) {
            if (adjMatrix[i][i]) {
                Object vertex = vertices.get(i);
                System.out.printf("Vertex %s: ", vertex.toString());
                for (int j = 0; j < vertNum; j++) {
                    if (adjMatrix[i][j] && i != j) {
                        System.out.print(vertices.get(j).toString() + " ");
                    }
                }
                System.out.println();
            }
        }
    }

    public int shortestPathDistance(T srcVert, T tarVert) {

        boolean[] marked = new boolean[this.vertNum];

        Queue<Pair> q = new ArrayDeque<>();
        marked[vertices.indexOf(srcVert)] = true;
        q.add(new Pair<T>(srcVert, 0));

        // while queue not empty, there are more vertices to check
        while (!q.isEmpty()) {
            Pair pair = q.remove();
            T vertex = (T) pair.getVert();
            int dist = pair.getDist();

            // found target vertex
            if (vertex.equals(tarVert)) {
                return dist;
            }

            // else keep expanding to neighbours
            for (T vert : this.neighbours(vertex)) {
                int index = vertices.indexOf(vert);
                if (!marked[index]) {
                    marked[index] = true;
                    q.add(new Pair<T>(vert, dist + 1));
                }
            }
        }

        return disconnectedDist;
    }

    private class Pair<T>
    {
        private T vertex;
        private int distance;

        public Pair(T vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public T getVert() {
            return vertex;
        }

        public int getDist() {
            return distance;
        }
    }

}