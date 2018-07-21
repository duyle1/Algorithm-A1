import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        File file = new File("/Users/Duy/Downloads/Source Code 2/src/graph1.in");
        File file2 = new File("/Users/Duy/Downloads/Source Code 2/src/500n0p4density.txt");
        File file3 = new File("/Users/Duy/Downloads/Source Code 2/src/500n1p2density.txt");
        File file4 = new File("/Users/Duy/Downloads/Source Code 2/src/5000n0p04density.txt");
        File file5 = new File("/Users/Duy/Downloads/Source Code 2/src/5000n0p7density.txt");

        try {
            // input graph
            InputStream in = new FileInputStream(file2);
            //in = new FileInputStream(file2);

            System.out.println("ADJACENT LIST GRAPH");
            AdjList<Integer> adjList1 = new AdjList<>(in);

//            for (int i = 0; i < 20; i++) {
//                adjList1.addVertex(i);
//            }

            adjList1.printEdges();
            adjList1.printVertices();
            System.out.println("");

            adjList1.addVertex(12);
            adjList1.addEdge(12, 0);




            adjList1.printEdges();
            adjList1.printVertices();
            System.out.println("");

            adjList1.removeVertex(12);

            adjList1.printEdges();
            adjList1.printVertices();
            System.out.println("");

            System.out.println("The shortest distance between 0 and 2 is " + adjList1.shortestPathDistance(0, 2));
            adjList1.addVertex(21);
            System.out.println("The shortest distance between 0 and 21 is " + adjList1.shortestPathDistance(0, 21));
            System.out.println("The shortest distance between 0 and 50 is " + adjList1.shortestPathDistance(0, 50));
            System.out.println("The shortest distance between 0 and 5 is " + adjList1.shortestPathDistance(0, 5));

            System.out.println("-------------------");
//
//            System.out.println("ADJACENT MATRIX GRAPH");
//            in = new FileInputStream(file);
//            AdjMatrix<Integer> adjMatrix = new AdjMatrix<>(in, 500);
//
//            adjMatrix.printEdges();
//            adjMatrix.printVertices();
//            System.out.println("");
//
//            adjMatrix.removeVertex(0);
//
//            adjMatrix.printEdges();
//            adjMatrix.printVertices();
//            System.out.println("");
//
//            adjMatrix.addVertex(0);
//            adjMatrix.addEdge(0, 1);
//
//
//            adjMatrix.printEdges();
//            adjMatrix.printVertices();
//            System.out.println("");
//
//            System.out.println("The shortest distance between 0 and 5 is " + adjMatrix.shortestPathDistance(0, 5));
//            adjMatrix.addVertex(12);
//            System.out.println("The shortest distance between 0 and 12 is " + adjMatrix.shortestPathDistance(0, 12));
//            System.out.println("The shortest distance between 0 and 50 is " + adjMatrix.shortestPathDistance(0, 50));
//
//            System.out.println(adjMatrix.shortestPathDistance(1, 5));


        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}
