import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author Brett Wilhelm
 * @author Zach Johnson
 *
 */

public class GraphProcessor1 {
	private AdjacencyList1 graph;//graph we will travel
	private Map<String, Boolean> isTraveled, undiscovered;//isTraveled is used to determine whether we have visited a vertex before, undiscovered holds all keys false so we can easily reset the map
	private LinkedList<String> neighbors;//this is a list holding the adjacent nodes from a given vertex
	private ArrayList<String> path = new ArrayList<String>();
	private Iterator<String> it;//used to iterate over neighbors
	private String BFS = "";//Used to hold the BFS from its helper method
	
	/**
	 * Constructs a graph based on the given filename and initializes the isTraveled map for either DFS or SCC usage
	 * @param graphData is the absolute path of the file holding the graph
	 * @throws FileNotFoundException if the file doesn't exist at the given path
	 */
	public GraphProcessor1(String graphData) throws FileNotFoundException {
		graph = new AdjacencyList1(graphData);
		isTraveled = new HashMap<String, Boolean>();
		
		for(String key : graph.getKeys()) {
			isTraveled.put(key, false);
		}
		undiscovered = new HashMap<String, Boolean>();
		undiscovered.putAll(isTraveled);
	}
	
	/**
	 * @param v String holding the vertex 
	 * @return Returns the number of edges originating from the given vertex
	 */
	public int outDegree(String v) {
		return graph.getOutDegree(v);
	}
	
	/**
	 * 
	 * @param u String representing the starting node of the graph
	 * @param v String representing the end node of our traversal
	 * @return if a path between u and v exists return an ArrayList<String> containing all the nodes on a DFS
	 */
	public ArrayList<String> bfsPath(String u, String v) {
		BFS  = "";
		path = new ArrayList<String>();	
		BFSUtil(u);
		Scanner s = new Scanner(BFS);
		
		while(s.hasNext()) {
			String cur = s.next();
			path.add(cur);
			if(cur.equals(v))
			{
				s.close();
				return path;
			}
		}
		s.close();
		return new ArrayList<String>();
	}
	
	//private helper methods used
	/**
	 * sets the table isTraveled at v and sets the value to true
	 * @param v vertex
	 */
	private void setIsTraveled(String v)
	{
		isTraveled.replace(v, true);
	}
	
	//shortest path helper methods
	/**
	 * computes the Breadth first traversal of graph sets the String BFS with the path from u
	 * @param u String representing the starting vertex
	 */
	private void BFSUtil(String u) {
		isTraveled.clear();
		isTraveled.putAll(undiscovered);
		LinkedList<String> queue = new LinkedList<String>();
		setIsTraveled(u);
		queue.add(u);
		
		while(queue.size() != 0) {
			String cur = queue.removeFirst();
			BFS += cur + " ";
			System.out.println(BFS+ "\n");
			
			neighbors = graph.getNeighbors(cur);
			it = neighbors.iterator();
			while(it.hasNext()) {
				cur = it.next();
				if(isTraveled.get(cur) == false) {
					queue.addLast(cur);
				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int diameter()
	{
		int i, k;
		int diameter;
		
		//for(k = 1; k <= graph.size(); k++)
			//for(i = 1; i <= graph.size(); i++)
				//bfsPath(graph.get(k), graph.get(i));
		System.out.println(graph.get(0));
		return 0;
	}
		
	//Strongly Connected Component helper methods
	/**
	 * 
	 * @param g AdjacencyList Representing the Graph
	 * @param v String representing the vertex currently being processed
	 * @param visited table used to determine whether or not a vertex has been traveled to 
	 */
	
}//end of graphProcessor



