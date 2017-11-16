import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Harrison Zey
 * @author Brett Wilhelm
 */

public class WikiCrawler {
	static final String BASE_URL = "https://en.wikipedia.org";
	private String seedUrl, filename, curUrl;
	private int counter, max, requestCount = 0;
	private static final String[] NOT_CONTAINED = {":", "#"};
	private static final String CONTAINS_CHECK = "/wiki/";
	private Queue<String> queue;
	private Map<String, Boolean> visited;
	private boolean toggleCounter;
	private AdjacencyList graph;
	private ArrayList<String> seedConnectionList, outputList, topics;
	
	/**
	 * 
	 * @param seedUrl
	 * @param max
	 * @param topics
	 * @param fileName
	 */
	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName) {
		this.seedUrl = seedUrl;
		this.max = max;
		this.topics = topics;
		this.filename = fileName;
		
		seedConnectionList = new ArrayList<String>();
		seedConnectionList.add(seedUrl);
		graph = new AdjacencyList(max);
	}
	
	/**
	 * 
	 */
	public void crawl() {
		toggleCounter = false;
		counter = 1;
		bfs(seedUrl);
		writeToFile(printData());
		
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 */
	public ArrayList<String> extractLinks(String doc) {
		
		ArrayList<String> totalConnectionList = new ArrayList<String>();
		ArrayList<String> neighborConnectionList = new ArrayList<String>();
		String input = "";
		int topicsFlag = 0;
		Scanner s = new Scanner(doc);
		s.useDelimiter("<p>|<P>");
		if(s.hasNext()) { s.next(); }
		
		while(s.hasNext()) {
			input += s.next();
		}
		
		for(String t: topics) {
			if(!(input.toLowerCase().contains(t.toLowerCase()))) {
				topicsFlag = 1;
				break;
			}
		}
		s.close();
		
		s = new Scanner(doc);
		s.useDelimiter("<p>|<P>");
		if(s.hasNext()) { s.next(); }	// Skips to just after first instance of <p> or <P>
		s.useDelimiter("href=\"|\"");
		
		if(topicsFlag == 0) {
			while(s.hasNext()) {
				input = s.next();
				
				if((input.toLowerCase()).contains(CONTAINS_CHECK) && !((input.toLowerCase()).contains(NOT_CONTAINED[0])) && !((input.toLowerCase()).contains(NOT_CONTAINED[1])) && (input.charAt(1)=='w')) {	// Ensures properly formatted links get through
					
					if(!(seedConnectionList.contains(input)) && counter < max && !(toggleCounter)) {
						totalConnectionList.add(input);
						seedConnectionList.add(input);
						counter++;
					}
					
					else if(seedConnectionList.contains(input) && !(neighborConnectionList.contains(input))  && toggleCounter && !(input.equals(curUrl))) {
						totalConnectionList.add(input);
						neighborConnectionList.add(input);
					}
				}
			}
		}
			
		s.close();
		
		if(counter >= max)	toggleCounter = true;
		return (totalConnectionList);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param v
	 */
	private void setVisited(String v) {
		visited.replace(v, true);
	}
	
	
	/**
	 * 
	 * @param url
	 */
	private void bfs(String url) {
		ArrayList<String> strList;
		LinkedList<String> neighbours;
		queue = new LinkedList<String>();
		visited = new HashMap<String, Boolean>();
		Iterator<String> iter;
		outputList = new ArrayList<String>();
		
		graph.addNode(url);
		setVisited(url);
		queue.add(url);
		
		while(queue.size() != 0) {
			curUrl = queue.remove();
			graph.addNode(curUrl);
			strList = extractLinks(pageSource(curUrl));
			
			for(int i = 0; i < strList.size(); i++) {
				graph.addNode(strList.get(i));
				graph.addEdge(curUrl, strList.get(i));
				visited.putIfAbsent(strList.get(i), false);
				String dupe = curUrl + "\t" + strList.get(i);
				if(!outputList.contains(dupe))
					outputList.add(dupe);
			}
			
			neighbours = graph.getNeighbors(curUrl);
			iter = neighbours.listIterator();
			
			while(iter.hasNext()) {
				String cur = iter.next();
				
				if(!(visited.get(cur))) {
					setVisited(cur);
					queue.add(cur);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param newUrl
	 * @return
	 */
	private String pageSource(String newUrl) {
		String progSource = "";
		URL url;
	    InputStream input = null;
	    BufferedReader br = null;
	    String line;

	    try {
	    	if(requestCount > 49) {
	    		requestCount = 0;
	    		TimeUnit.SECONDS.sleep(3);
	    	}
	    	
	        url = new URL(BASE_URL + newUrl);	        
	        input = url.openStream();
	        requestCount++;
	        br = new BufferedReader(new InputStreamReader(input));
	        
	        if ((line = br.readLine()) != null) { progSource += line; }
	        while ((line = br.readLine()) != null) { progSource += "\n" + line; }
	        
	        return progSource;
	        
	    }
	    catch (MalformedURLException m)	{ m.printStackTrace(); }
	    catch (IOException i) 			{ i.printStackTrace(); }
	    catch (InterruptedException e) 	{ e.printStackTrace(); }
		finally {
	        try {
	            if (input != null)	input.close();
	        }
	        catch (IOException i) { }
	    }
		return null;
	}
	
	/**
	 * 
	 * @param data
	 */
	private void writeToFile(String data) {
		try {
		    PrintWriter writer = new PrintWriter(filename, "UTF-8");
		    writer.print(data);
		    writer.close();
		} catch (IOException e) { }
	}
	
	/**
	 * 
	 * @return
	 */
	private String printData() {
		String data = max + "\n";
		
		for(int i = 0; i < outputList.size(); i++) {
			data += outputList.get(i);
			if(i < outputList.size()-1)	data += "\n";
		}
		return data;
	}
}