import java.io.IOException;
import java.util.ArrayList;

public class testDrive {

	public static void main(String[] args) throws IOException {
		long time_start, time;
		ArrayList<String> topics = new ArrayList<String> ();
//		topics.add("Iowa");
		
		WikiCrawler1 wc = new WikiCrawler1("/wiki/Complexity_Theory", 200, topics, "test.txt");
		
		time_start = System.nanoTime();
		wc.crawl();
		time = System.nanoTime() - time_start;
		
//		System.out.println(wc.getPrintData());
		
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
		
		System.out.println("Max Vertices: " + wc.getGraph());
		System.out.println("Time taken to execute crawl(): " + properFormat(time));
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