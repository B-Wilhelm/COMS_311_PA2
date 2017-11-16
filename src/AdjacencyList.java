import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Brett Wilhelm
 * @author Harry Zey
 */

public class AdjacencyList {
	private Map<String, LinkedList<String>> adj;
	private final int v;
	
	/**
	 * Default Constructor
	 */
	public AdjacencyList() { v = 0; }
	
	/**
	 * 
	 * @param v
	 */
	public AdjacencyList(int v) {
		adj = new HashMap<String, LinkedList<String>>();
		this.v = v;
	}
	
	/**
	 * 
	 * @param fp
	 * @throws FileNotFoundException
	 */
	public AdjacencyList(String fp) throws FileNotFoundException {
		File f = new File(fp);
		Scanner s = new Scanner(f);
		String l1, n1, n2;
		adj = new HashMap<String, LinkedList<String>>();
		v = s.nextInt();
		
		s.nextLine();
		
		while(s.hasNextLine()) {
			l1 = s.nextLine();
			Scanner s2 = new Scanner(l1); 
			n1 = s2.next();
			n2 = s2.next();
			addNode(n1);
			addNode(n2);
			addEdge(n1, n2);
			s2.close();
		}
		s.close();
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public boolean addNode(String node) {
		 if(adj.putIfAbsent(node, new LinkedList<String>()) == null) { return true; }
		 else { return false; }
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 */
	public void addEdge(String from, String to) {
		adj.get(from).add(to);	
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public LinkedList<String> getNeighbors(String node) { return adj.get(node); }
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public int getOutDegree(String node) {
		if(adj.get(node) == null)	return 0;
		
		return adj.get(node).size();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMaxVertices() { return v; }
	
	/**
	 * 
	 * @return
	 */
	public Set<String> getKeys() { return adj.keySet(); }

	/**
	 * 
	 * @return
	 */
	public int size() {
		return adj.size();
	}	
}
