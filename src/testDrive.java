import java.io.IOException;
import java.util.ArrayList;

public class testDrive {

	public static void main(String[] args) throws IOException {
		ArrayList<String> topics = new ArrayList<String> ();
		topics.add("Iowa State");
		topics.add("Ames");
		
		WikiCrawler1 wc = new WikiCrawler1("/wiki/Iowa_State_University", 20, topics, "test.txt");
		
		wc.crawl();
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
	}
}