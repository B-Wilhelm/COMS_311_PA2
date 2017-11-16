import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Brett Wilhelm
 * @author Harrison Zey
 */

public class GraphProcessor {
	private AdjacencyList graph;
	private Map<String, Boolean> mapOfTraveled, mapOfUndiscovered;
	private LinkedList<String> listOfNeighbors;
	private ArrayList<String> path = new ArrayList<String>();
	private Iterator<String> iter;
	private String bfs = "";
	
	/**
	 * 
	 * @param graphData
	 * @throws FileNotFoundException
	 */
	public GraphProcessor(String graphData) throws FileNotFoundException {
		graph = new AdjacencyList(graphData);
		mapOfTraveled = new HashMap<String, Boolean>();
		
		for(String key: graph.getKeys()) { mapOfTraveled.put(key, false); }
		
		mapOfUndiscovered = new HashMap<String, Boolean>();
		mapOfUndiscovered.putAll(mapOfTraveled);
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public int outDegree(String v) { return graph.getOutDegree(v); }
	
	/**
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public ArrayList<String> bfsPath(String u, String v) {
		bfs = BFSUtil(u,v);
		path = new ArrayList<String>();	
		Scanner s = new Scanner(bfs);
		
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
	
	/**
	 * 
	 * @return
	 */
	public int diameter() {
		ArrayList<String> curPathName;
		String[] urlArr = new String[graph.size()];
		int i, k, diameter, maxDiameter = 0;
		
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
	
	/**
	 * 
	 * @param v
	 */
	private void setTraveled(String v) { mapOfTraveled.replace(v, true); }
	
	/**
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	private String BFSUtil(String u, String v) {
		mapOfTraveled.clear();
		mapOfTraveled.putAll(mapOfUndiscovered);
		LinkedList<Node> queue = new LinkedList<Node>();
		LinkedList<String> emptyList = new LinkedList<String>();
		setTraveled(u);
		queue.add(new Node(u, u));
		
		while(queue.size() != 0) {
			Node cur = queue.removeFirst();
			if(cur.url.equals(v))
				return cur.totalUrl;
			
			listOfNeighbors = graph.getNeighbors(cur.getUrl());
			
			if(listOfNeighbors == null) listOfNeighbors = emptyList;
			
			iter = listOfNeighbors.iterator();
			
			while(iter.hasNext()) {
				String curUrl = iter.next();
				if(!(mapOfTraveled.get(curUrl))) {
					setTraveled(curUrl);
					queue.add(new Node(curUrl, cur.getTotUrl() + " " + curUrl));
				}
			}
		}
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @author Brett W
	 * @author Harrison Zey
	 */
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





