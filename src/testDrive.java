import java.io.IOException;

public class testDrive {

	public static void main(String[] args) throws IOException {
		WikiCrawler1 wc = new WikiCrawler1("/wiki/Complexity_theory", 20, "test.txt");
		
		System.out.println(wc.extractLinks(wc.getSource()));
		
		
	}
}