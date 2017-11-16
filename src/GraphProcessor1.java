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
		BFS = BFSUtil(u, v);
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
	 * @return 
	 */
	private String BFSUtil(String u, String v) {
		isTraveled.clear();
		isTraveled.putAll(undiscovered);
		LinkedList<Node> queue = new LinkedList<Node>();
		setIsTraveled(u);
		queue.add(new Node(u, u));
		
		while(queue.size() != 0) {
			Node cur = queue.removeFirst();
			if(cur.url.equals(v))
				return cur.totalUrl;
			
			neighbors = graph.getNeighbors(cur.getUrl());
			it = neighbors.iterator();
			
			while(it.hasNext()) {
				String curUrl = it.next();
				if(!(isTraveled.get(curUrl))) {
					setIsTraveled(curUrl);
					queue.add(new Node(curUrl, cur.getTotUrl() + " " + curUrl));
				}
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public int diameter() {
		int i, k;
		int diameter;
		
		//for(k = 1; k <= graph.size(); k++)
			//for(i = 1; i <= graph.size(); i++)
				//bfsPath(graph.get(k), graph.get(i));
		//System.out.println(graph.get(0));
		return 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public class Node {
		String totalUrl;
		String url;
		public Node(String u, String tot){
			url = u;
			totalUrl = tot;
		}
		
		public String getUrl() {
			return url;
		}
		
		public String getTotUrl() {
			return totalUrl;
		}
	}
	
}//end of graphProcessor





