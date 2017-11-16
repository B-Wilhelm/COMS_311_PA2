import java.io.IOException;
import java.util.ArrayList;

public class testDrive {

	public static void main(String[] args) throws IOException {
		long time_start, time;
		ArrayList<String> topics = new ArrayList<String> ();
//		topics.add("Iowa State");
//		topics.add("Cyclones");
//		topics.add("geynfdskjfsdf");
		
		WikiCrawler wc = new WikiCrawler("/wiki/Computer_Science", 2, topics, "WikiCS.txt");
		
		time_start = System.nanoTime();
		wc.crawl();
		time = System.nanoTime()-time_start;
		
		GraphProcessor gp = new GraphProcessor("WikiCS.txt");
		
		maxOutDegree(gp);
	}
	
	private static int maxOutDegree(GraphProcessor gp) {
		String[] urlArr = new String[gp.graph.size()];
		urlArr = gp.graph.getKeys().toArray(new String[0]);
		
		System.out.println(gp.diameter());
		
		for(int i = 0; i < urlArr.length; i++) {
			System.out.println("OutDegree " + urlArr[i] + ": " + gp.outDegree(urlArr[i]));
		}
		
		for(int i = 0; i < urlArr.length; i++) {
			System.out.println("Centrality " + urlArr[i] + ": " + gp.centrality(urlArr[i]));
		}
		
		return 0;
	}
	
	private static String properFormat(long time) {
		if (time >= 1000000000) {
			return time/1000000000 + "s";
		}
		else if (time >= 1000000) {
			return time/1000000 + "ms";
		}
		else {
			return time + "ns";
		}
		
	}
}