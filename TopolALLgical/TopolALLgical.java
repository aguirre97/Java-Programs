/*
Alexandra Aguirre
3-31-17
COP 3503
NID: al373180
*/

import java.io.*;
import java.util.*;
public class TopolALLgical
{
    static boolean matrix[][];
    static HashSet<String> solution;

    public static HashSet<String> allTopologicalSorts(String filename) throws IOException
    {
        // if there is an input file, go ahed with the program
        try
        {
            Scanner in = new Scanner(new File(filename));

            int n = in.nextInt();
            
            // store the input file in an adjacency matrix
            matrix = new boolean[n+1][n+1];

            for(int i = 1; i <= n; i++)
            {
                int edges = in.nextInt();

                for(int j = 1; j <= edges; j++)
                {
                    int vertex = in.nextInt();
                    matrix[i][vertex] = true;
                }
            }

            in.close();

            solution = new HashSet<String>();
            
            // keep track of the nodes that will be used and the number of incoming edges
            HashMap<Integer, Integer> usedNodes = new HashMap<>();
            HashMap<Integer, Integer> incoming = new HashMap<>();

            // hold each of the topological sorts created
            ArrayList<String> topologicalList = new ArrayList<>();

            for(int i = 1; i < matrix.length; i++)
            {
                for(int j = 1; j < matrix.length; j++)
                {
                    if(incoming.get(j) == null)
                    {
                        if(matrix[i][j] == true)
                            incoming.put(j, 1);
                        else
                            incoming.put(j, 0);
                    }
                    else
                    {
                        if(matrix[i][j] == true)
                            incoming.put(j, (incoming.get(j) + 1));
                    }
                }
            }

            // recursion
            // start with 0 used nodes
            allTopologicalSorts(0, topologicalList, usedNodes, incoming);

            //System.out.println(solution);
            return solution;
        }
        // there is no file, return an empty HashSet
        catch(IOException e)
        {
            // so program does not crash
            matrix = new boolean[1][1];

            // return empty
            return new HashSet<String>();
        }
        
    }

// used nodes, // incoming
    public static HashSet<String> allTopologicalSorts(int numUsed, ArrayList<String> topologicaList, HashMap<Integer, Integer> usedNodes, HashMap<Integer, Integer> incoming)  
    {
        // base case
        // all nodes have been used
        if(numUsed == matrix.length - 1)
        {   
            solution.add(makeString(topologicaList));
            return solution;
        }
    
        for(int i = 1; i < matrix.length; i++)
        {
            // if it has 0 incoming and it hasnt been used
            // add it to the hashmap
            if(incoming.get(i) ==  0 && !(usedNodes.containsValue(i)))
            {
                // mark the node as usedand add it to the topologicalList
                usedNodes.put(numUsed, i);
                topologicaList.add(numUsed, Integer.toString(i));
                
                // increment the number of nodes we have currently used
                numUsed++;
                
                for(int j = 1; j < matrix.length; j++)
                {   
                    if(matrix[i][j] == true)
                        // update node & decrease incoming 
                        incoming.put(j, (incoming.get(j) - 1));
                }
                
                // recursion!
                allTopologicalSorts(numUsed, topologicaList, usedNodes, incoming);

                //undo state change
                numUsed--;
                usedNodes.remove(numUsed);
                topologicaList.remove(numUsed);
                
                for(int j = 1; j < matrix.length; j++)
                    if(matrix[i][j] == true)
                        incoming.put(j, (incoming.get(j) + 1));
                //System.out.println(topologicaList);
            }
        }
        // return all possible solutions
        return solution;
    }

    // turn the topologicalList into a string by using a stringbuilder
    public static String makeString(ArrayList<String> topologicaList)
    {
        int num = topologicaList.size();

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < num; i++)
            sb.append(topologicaList.get(i).toString() + ((i == num - 1) ? "" : " "));
        
        // make sure the stringbuilder is returned as a string
        return sb.toString();
    }


    public static double difficultyRating()
    {
        return 3.6;
    }

    public static double hoursSpent()
    {
        return 9;
    }

}