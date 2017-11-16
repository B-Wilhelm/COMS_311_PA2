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
 * @author Harrison Zey
 *
 */

public class GraphProcessor {
	private AdjacencyList graph;
	private Map<String, Boolean> mapOfTraveled, mapOfUndiscovered;
	private LinkedList<String> neighbors;
	private ArrayList<String> path = new ArrayList<String>();
	private Iterator<String> it;
	private String BFS = "";
	
	/**
	 * Creates a graph using a given filename
	 * @param graphData is the absolute path of the file holding the graph
	 * @throws FileNotFoundException if the file doesn't exist at the given path
	 */
	public GraphProcessor(String graphData) throws FileNotFoundException {
		graph = new AdjacencyList(graphData);
		mapOfTraveled = new HashMap<String, Boolean>();
		
		for(String key : graph.getKeys()) {
			mapOfTraveled.put(key, false);
		}
		mapOfUndiscovered = new HashMap<String, Boolean>();
		mapOfUndiscovered.putAll(mapOfTraveled);
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
			if(cur.equals(v)) {
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
	private void setIsTraveled(String v) {
		mapOfTraveled.replace(v, true);
	}
	
	//shortest path helper methods
	/**
	 * computes the Breadth first traversal of graph sets the String BFS with the path from u
	 * @param u String representing the starting vertex
	 * @return 
	 */
	private String BFSUtil(String u, String v) {
		mapOfTraveled.clear();
		mapOfTraveled.putAll(mapOfUndiscovered);
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
				if(!(mapOfTraveled.get(curUrl))) {
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
		int i, k, diameter, maxDiameter = 0;
		ArrayList<String> curPathName;
		String[] urlArr = new String[graph.size()];
		
		urlArr = graph.getKeys().toArray(new String[0]);
		
		for(k = 0; k < urlArr.length; k++) {
			for(i = 0; i < urlArr.length; i++) {
				if (k != i) {
					curPathName = bfsPath(urlArr[k], urlArr[i]);
					diameter = curPathName.size();
					if (diameter > maxDiameter)
						maxDiameter = diameter;
				}
			}
		}
		
		return maxDiameter;
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public int centrality(String v)
	{
		int i, j, k, centrality = 0;
		ArrayList<String> curPathName;
		String[] urlArr = new String[graph.size()];
		
		urlArr = graph.getKeys().toArray(new String[0]);
		
		for(k = 0; k < urlArr.length; k++) {
			for(i = 0; i < urlArr.length; i++) {
				if (k != i) {
					curPathName = bfsPath(urlArr[k], urlArr[i]);
					if(curPathName.size() > 2) {
						for(j = 1; j < curPathName.size() - 1; j++)
							if(v.equals(curPathName.get(j)))
								centrality++;
					}
				}
			}
		}
		
		return centrality;
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
	
}





