import java.io.IOException;
import java.util.ArrayList;

public class testDrive {

	public static void main(String[] args) throws IOException {
		long time_start, time;
		ArrayList<String> topics = new ArrayList<String> ();
		topics.add("Iowa State");
		topics.add("Cyclones");
		topics.add("geynfdskjfsdf");
		
		WikiCrawler wc = new WikiCrawler("/wiki/Iowa_State_University", 20, topics, "test.txt");
		GraphProcessor gp = new GraphProcessor("test.txt");
		
//		System.out.println(gp.bfsPath("/wiki/Story_County,_Iowa","/wiki/Model_farm"));
//		System.out.println(gp.outDegree("/wiki/Story_County,_Iowa"));
		
		time_start = System.nanoTime();
		wc.crawl();
		time = System.nanoTime() - time_start;
		
		System.out.println("Time taken to execute crawl(): " + properFormat(time));
		
		System.out.println(wc.getPrintData());
		
		System.out.println("Diameter: " + gp.diameter());
		System.out.println("Centrality: " + gp.centrality("/wiki/Iowa"));
		
//		System.out.println(wc.extractLinks(wc.getPageSource(wc.getSource())));
		
//		ArrayList<String> boi = wc.extractLinks(wc.getPageSource(wc.getSource()));
		
//		System.out.println("\nExtractLinks() Output:");
//		for(int i = 0; i < boi.size(); i++) {
//			System.out.println(boi.get(i));
//		}
		
//		System.out.println("\nseedList() Output:");
//		for(int i = 0; i < wc.seedConnectionList.size(); i++) {
//			System.out.println(wc.seedConnectionList.get(i));
//		}
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