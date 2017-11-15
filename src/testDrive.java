import java.io.IOException;

public class testDrive {

	public static void main(String[] args) throws IOException {
		WikiCrawler1 wc = new WikiCrawler1("/wiki/Iowa_State_University", 100, "test.txt");
		
		System.out.println(wc.extractLinks(wc.getSource()));
		
		
	}
}