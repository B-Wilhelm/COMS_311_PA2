import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * @author Brett Wilhelm
 * @author Zach Johnson
 *
 */

public class GraphProcessor1 {
	private int V;// Max number of vertices
	private AdjacencyList1 graph;//graph we will travel
	private Map<String, Boolean> isTraveled, undiscovered;//isTraveled is used to determine whether we have visited a vertex before, undiscovered holds all keys false so we can easily reset the map
	private LinkedList<String> neighbors;//this is a list holding the adjacent nodes from a given vertex
	private ArrayList<String> path = new ArrayList<String>();
	private Iterator<String> it;//used to iterate over neighbors
	private String SCC = "";//String holding Strongly connected components, used so that the different SCC methods have a consistant understand of SCC
	private int numSCC = 0;
	public String BFS = "";//Used to hold the DFS from its helper method
	
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
		
		computeSCCs();
	}
	/**
	 * @param v String holding the vertex 
	 * @return Returns the number of edges originating from the given vertex
	 */
	public int outDegree(String v) {
		return graph.getOutDegree(v);
	}
	/**
	 * I use the SCC string and make Arraylists out of that string to handle an unknown number of arrayLists after each list is created i check if both nodes are within that list
	 * @param u String first component
	 * @param v String second component
	 * @return true if first component and second component are within the same Strongly Connected Component
	 */
	public boolean sameComponent(String u, String v) {
		//computeSCCs(); done within the constructor
		ArrayList<String> SCCList;
		Scanner s1 = new Scanner(SCC);
		String line;
		while(s1.hasNextLine()) {
			line = s1.nextLine();
			Scanner s2 = new Scanner(line);
			SCCList = new ArrayList<String>();
			while(s2.hasNext())
			{
				SCCList.add(s2.next());
			}
			if(SCCList.contains(u)&& SCCList.contains(v))
			{
				s1.close();
				s2.close();
				return true;
			}	
			s2.close();
		}
		s1.close();
		return false;
	}
	
	/**
	 * 
	 * @param v a vertex within a Strongly connected Component
	 * @return an ArrayList containing all vertices that share a Strongly Connected Component with v
	 */
	public ArrayList<String> componentVerticies(String v) {
		//computeSCCs(); done within the constructor
		ArrayList<String> SCCList = new ArrayList<String>();
		Scanner s1 = new Scanner(SCC);
		String line;
		while(s1.hasNextLine()) {
			line = s1.nextLine();
			Scanner s2 = new Scanner(line);
			SCCList = new ArrayList<String>();
			while(s2.hasNext())
			{
				SCCList.add(s2.next());
			}
			if(SCCList.contains(v))
			{
				s1.close();
				s2.close();
				return SCCList;
			}	
			s2.close();
		}
		s1.close();
		return SCCList;
	}

	/**
	 * 
	 * @return the number of Strongly Connected Components within the graph created by the constructor
	 */
	public int numComponents() {
		return numSCC;
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
		
	//Strongly Connected Component helper methods
	/**
	 * 
	 * @param g AdjacencyList Representing the Graph
	 * @param v String representing the vertex currently being processed
	 * @param visited table used to determine whether or not a vertex has been traveled to 
	 */
	
	private void DFSHelper(AdjacencyList1 g, String v,  Map<String, Boolean> visited) {
		setIsTraveled(v);
		SCC += v + " ";
		
		String cur;
		neighbors = g.getNeighbors(v);
		it = neighbors.iterator();
		
		while(it.hasNext()) {
			 cur = it.next();
			 
			if(visited.get(cur) == false) {
				DFSHelper(g, cur, visited);
			}
		}
	}
	
	/**
	 * 
	 * @return returns a graph with all the directed edges point the other way
	 */
	private AdjacencyList1 getTranspose() {
		AdjacencyList1 tmp = new AdjacencyList1(V);
		
		for(String key : graph.getKeys()){
			tmp.addNode(key);
			neighbors = graph.getNeighbors(key);
			it = neighbors.iterator();
			while(it.hasNext()) {
				String s = it.next();
				tmp.addNode(s);
				tmp.addEdge(s, key);
			}
		}
		return tmp;
	}
	
	/**
	 * 
	 * @param v vertex started from when determining order
	 * @param visited Table used to determine whether or not a vertex has been traveled to 
	 * @param s Stack used to hold the vertices in the recursive DFS traversal
	 */
	private void fillOrder(String v, Map<String, Boolean> visited, Stack<String> s) {
		setIsTraveled(v);
		
		neighbors = graph.getNeighbors(v);
		it = neighbors.iterator();
		while(it.hasNext()) {
			String cur = it.next();
			if(visited.get(cur) == false) {
				fillOrder(cur, visited, s);
			}
		}
		
		s.push(new String(v));
	}
	/**
	 * used to compute the Strongly connected components, modifies the SCC string to contain each SCC on a new line
	 */
	private void computeSCCs() {
		SCC = "";//resets the SCC string to prevent the list of SCCS from being duplicated
		isTraveled.clear();
		isTraveled.putAll(undiscovered);
		Stack<String> s1 = new Stack<String>();
		
		//fills the vertices in the stack by time to reach each node
		for(String key : graph.getKeys()) {
			if(isTraveled.get(key) == false) {
				fillOrder(key, isTraveled, s1);
			}
		}
		//creates a reversed graph to determine whether a path exists from the same starting places in the opposite direction
		AdjacencyList1 reverse = getTranspose();
		
		//clears the isTraveled map to perform a second DFS 
		isTraveled.clear();
		isTraveled.putAll(undiscovered);
		
		//process all vertices by order given from fillOrder
		while(s1.isEmpty() == false) {
			String v = s1.pop();
			
			if(isTraveled.get(v) == false) {
				DFSHelper(reverse, v, isTraveled);//determines and potentially adds a vertex to it SCC
				SCC += "\n";//starts a new SCC
				numSCC++;//increment the counter of sccs
			}
		}
	}//end of computeSCCs
}//end of graphProcessor



