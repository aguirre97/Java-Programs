import java.io.*;
import java.util.*;

/*
Alexandra Aguirre
NID: al373180
COP 3503
3-22-17
*/


public class ConstrainedTopoSort
{
    static boolean matrix [][];
    static int incoming [];
    static int hasCycle = 0;

    /*
    This is a modified version of Dr. Szumlanski's toposort.java
    */
    public ConstrainedTopoSort(String fileName) throws IOException
    {
        // Scan in the graph file
        Scanner in = new Scanner(new File(fileName));

        // scans in the number of nodes in the graph
        int n = in.nextInt();

        // adjacency matrix
        matrix = new boolean [n][n];

        // goes through the entire graph file
        for(int i = 0; i < n; i++)
        {
            int numVert = in.nextInt();

            /* fills in the adjacency matrix,
              lets us know if there is an edge between two nodes (true) or not (false) */
            for(int j = 0; j < numVert; j++)
            {
                int num = in.nextInt();
                matrix[i][num-1] = true;
            }
        }
    
        in.close();

        // keep track of incoming edges
        incoming = new int[matrix.length];

        // count the number of incoming edges for each node
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix.length; j++)
                incoming[j] += (matrix[i][j] ? 1 : 0);

        Queue<Integer> q = new ArrayDeque<Integer>();

        int cnt = 0;

        /* keep track of incoming edges, used for the queueing process
         and to not confuse it with int [] incoming b/c that is used in the 
         hasConstrainedTopoSort() method */
        int [] incomingQ = new int[matrix.length];

       for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix.length; j++)
                incomingQ[j] += (matrix[i][j] ? 1 : 0);

        // Any vertex with zero incoming edges is ready to be visited, so add it to
        // the queue.
        for (int i = 0; i < matrix.length; i++)
            if (incomingQ[i] == 0)
                q.add(i);

        while (!q.isEmpty())
        {
            // Pull a vertex out of the queue and add it to the topological sort.
            int node = q.remove();

            // Count the number of unique vertices we see.
            ++cnt;

            // All vertices we can reach via an edge from the current vertex should
            // have their incoming edge counts decremented.
            for (int i = 0; i < matrix.length; i++)
                if (matrix[node][i] && --incomingQ[i] == 0)
                    q.add(i);
        }

        // If we pass out of the loop without including each vertex in our
        // topological sort, we must have a cycle in the graph.
        if (cnt != matrix.length)
            hasCycle = 1;
    }
   
    public boolean hasConstrainedTopoSort(int x, int y)
    {
        // this means there is a cycle in the graph
        if(hasCycle == 1) 
            return false;
            
        if(x == y)
            return false;

        /* in order to have a ConstrainedTopoSort, x has to have less incoming
         edges than y and there cannot be an edge from y to x */
        if(incoming[x-1] <= incoming[y-1])
            if(matrix[y-1][x-1] != true)
                return true;

        return false;
    }

    public static double difficultyRating()
    {
        return 2.0;
    }

    public static double hoursSpent()
    {
        return 4.0;
    }
}