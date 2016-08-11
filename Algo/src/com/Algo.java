package com;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.management.ThreadMXBean;
import java.util.Scanner;
import java.lang.management.ManagementFactory;

public class Algo {
    // number of nodes
    private static int nodes;

    // method to find the node with minimum cost from the set of nodes not yet included in MST
    int minCost(int cost[], Boolean setOfMst[])
    {
        int sml = Integer.MAX_VALUE;
        int sml_index=-1;
        for (int i = 0; i < nodes; i++)
            if (setOfMst[i] == false && cost[i] < sml)
            {
                sml = cost[i];
                sml_index = i;
            }
        return sml_index;
    }

    // method to write the MST to the output file
    void writeMST(int parent[], int n, int graph[][], File file) throws IOException
    {
    	long startTime = System.nanoTime();
		FileWriter fw = new FileWriter(file, false);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter outFile = new PrintWriter(bw);

		outFile.println("Total number of nodes = "+n);
		outFile.println("Total number of edges in the minimum spanning three = "+(parent.length-1));
		int tCost =0;
        outFile.println("List of edges & their costs:");
        for (int i = 1; i < nodes; i++)
        {
            outFile.println("("+parent[i]+", "+ i+")"+"   "+"edge cost:  "+ graph[i][parent[i]]);
            tCost = tCost + graph[i][parent[i]];
        }
        outFile.println("Total cost of minimum spanning tree is = "+tCost);
        outFile.println();

		// division by 1000000 to get milliseconds
		outFile.println("Execution Times (Milliseconds): " + (System.nanoTime() - startTime) / 1000000);
//		outFile.println("UserTime   = " + getUserT()/1000000);
//		outFile.println("SystemTime = " + getSysT()/1000000);
//		outFile.println("CpuTime    = " + getCpuT()/1000000);

		int mib = 1024*1024;

		Runtime rt = Runtime.getRuntime();

		outFile.println("Memory Consumption (Mebibytes): " + (rt.totalMemory() - rt.freeMemory()) / mib);
//		outFile.println("Free Memory  = " + rt.freeMemory() / mib);
//		outFile.println("Total Memory = " + rt.totalMemory() / mib);
//		outFile.println("Max Memory   = " + rt.maxMemory() / mib);

        outFile.close();
    }

    // method to prepare and write MST for the graph
    void prepareMST(int givenFileGraph[][], File file) throws IOException
    {
        Boolean setOfMst[] = new Boolean[nodes];
        int cost[] = new int [nodes];
        int mst[] = new int[nodes];

        for (int i = 0; i < nodes; i++)
        {
            cost[i] = Integer.MAX_VALUE;
            setOfMst[i] = false;
        }

        cost[0] = 0;
        mst[0] = -1; // root

        for (int counter = 0; counter < nodes-1; counter++)
        {
            // select the minimum cost node
            int n = minCost(cost, setOfMst);
            // include the selected node in the MST set
            setOfMst[n] = true;

            for (int i = 0; i < nodes; i++)

                if (givenFileGraph[n][i]!=0 && setOfMst[i] == false && givenFileGraph[n][i] <  cost[i])
                {
                    mst[i]  = n;
                    cost[i] = givenFileGraph[n][i];
                }
        }

        // write the prepared MST
        writeMST(mst, nodes, givenFileGraph, file);
    }

    public static void main (String[] args) throws IOException
    {
    	System.out.println("Enter valid number of nodes: ");
    	Scanner scanner = new Scanner(System.in);
    	String choice = scanner.nextLine();
    	File f = null;
    	File file = null;
    	scanner.close();

    	int success;
		switch (choice) {
		case "10":
			f = new File("AdjacencyMatrix_of_Graph_G_N_10.txt");
			file = new File("Result_Graph_G_N_10.txt");
			success = 1;
			break;
		case "20":
			f = new File("AdjacencyMatrix_of_Graph_G_N_20.txt");
			file = new File("Result_Graph_G_N_20.txt");
			success = 1;
			break;
		case "50":
			f = new File("AdjacencyMatrix_of_Graph_G_N_50.txt");
			file = new File("Result_Graph_G_N_50.txt");
			success = 1;
			break;
		case "100":
			f = new File("AdjacencyMatrix_of_Graph_G_N_100.txt");
			file = new File("Result_Graph_G_N_100.txt");
			success = 1;
			break;
		case "1000":
			f = new File("AdjacencyMatrix_of_Graph_G_N_1000.txt");
			file = new File("Result_Graph_G_N_1000.txt");
			success = 1;
			break;
		case "10000":
			f = new File("AdjacencyMatrix_of_Graph_G_N_10000.txt");
			file = new File("Result_Graph_G_N_10000.txt");
			success = 1;
			break;
		default:
			System.out.println("Invalid choice. Program will now exit.");
			success = -1;
			break;
		}

		if (success == 1)
		{
			String s = null;
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
	
	//		List<List<Integer>> listOfLists = new ArrayList<List<Integer>>();
	//		List<Integer> list = new ArrayList<Integer>();
			String chunk7=null;
	
			s = br.readLine();
			int n = s.length()/7;
			nodes = n;
			int graphArray[][] = new int[n][n];
	
			int i = 0, j = 0;
			while (s != null) {
	//			System.out.println(s);
				chunk7=null;
				for (int a=0;a<=s.length()-7;a=a+7)
				{
					chunk7=s.substring(a, a+7);
	//				System.out.println(Integer.parseInt(chunk7.trim()));
					graphArray[i][j] = Integer.parseInt(chunk7.trim());
	//				System.out.println("graph["+i+"]["+j+"] = "+graph[i][j]);
					j++;
				}
				j=0;
				i++;
				s = br.readLine();
			}
			br.close();
	
	//		listOfLists.add(list);
	//		System.out.println(listOfLists);
	//
	//		Integer[][] array = new Integer[listOfLists.size()][];
	//		for (int i = 0; i < listOfLists.size(); i++) {
	//		    List<Integer> row = listOfLists.get(i);
	//		    array[i] = row.toArray(new Integer[row.size()]);
	//		}
	//
	//		int[][] graph = null;
	//		for (int i = 0; i < array.length; i++) {
	//
	//		}
	
			Algo algo = new Algo();
	
	        // Write the MST
	        algo.prepareMST(graphArray, file);
		}
    }

	/** CPU time */
	public static long getCpuT() {
		ThreadMXBean myBean = ManagementFactory.getThreadMXBean();
		return myBean.isCurrentThreadCpuTimeSupported() ? myBean.getCurrentThreadCpuTime() : 0L;
	}

	/** user time */
	public static long getUserT() {
		ThreadMXBean myBean = ManagementFactory.getThreadMXBean();
		return myBean.isCurrentThreadCpuTimeSupported() ? myBean.getCurrentThreadUserTime() : 0L;
	}

	/** system time */
	public static long getSysT() {
		ThreadMXBean myBean = ManagementFactory.getThreadMXBean();
		return myBean.isCurrentThreadCpuTimeSupported()
				? (myBean.getCurrentThreadCpuTime() - myBean.getCurrentThreadUserTime()) : 0L;
	}
}
