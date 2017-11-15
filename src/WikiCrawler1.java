import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Brett Wilhelm
 * @author Zach Johnson
 *
 */

public class WikiCrawler1 {
	static final String BASE_URL = "https://en.wikipedia.org";
	private ArrayList<String> list;
	private String seedUrl, fileName;
	private int max, min = 0;
	private Scanner s, l;	// Scanner for entire source code, Scanner for individual links
	private String source, scannedText, progSource;
	private static final String CONTAINS_CHECK = "/wiki/";
	private static final String[] NOT_CONTAINED = {":", "#"};
	
	
	/*
	 * @param seedURL String representing relative address of the Seed URL
	 * @param max Integer primitive representing the max number of pages to crawl
	 * @param fileName String representing the name of the file that the graph will be written to
	 */
	public WikiCrawler1(String seedUrl, int max, String fileName) throws IOException {
		this.seedUrl = seedUrl;
		this.max = max;
		this.fileName = fileName;
		
		source = getPageSource(seedUrl);
	}
	
	
	/*
	 * @param doc String that represents the source code of an html page
	 */
	
	public ArrayList<String> extractLinks(String doc) {
		ArrayList<String> newList = new ArrayList<String>();
		scannedText = "";
		
		s = new Scanner(doc);	// Scanner for whole html source code
		s.useDelimiter("<p>|<P>");
		
		if(s.hasNext()) {			
			s.next();	// Skips to just after first instance of <p> or <P>
		}
		
		s.useDelimiter("href=\"|\"");
		
		while(s.hasNext()) {
			scannedText = s.next();
			
			if((scannedText.toLowerCase()).contains(CONTAINS_CHECK) && !((scannedText.toLowerCase()).contains(NOT_CONTAINED[0])) && !((scannedText.toLowerCase()).contains(NOT_CONTAINED[1])) && (scannedText.charAt(1)=='w')) {	// Ensures properly formatted links get through
				if((min < max) && !(newList.contains(scannedText)) && !(scannedText.equals(seedUrl))) {	// Ensures links aren't duplicates or self-loops and stops collecting at "max" value
					System.out.println(seedUrl + " : " + scannedText);
					newList.add(scannedText);
					min++;
				}
			}
		}
		
		return newList;
	}
	
	
	public void crawl() {
		
	}
	
	
	/////////////////////////////////////////
	
	
	public String getPageSource(String urlS) {	// Takes in relative URL
		progSource = "";
		
		URL url;
	    InputStream input = null;
	    BufferedReader br = null;
	    String line;

	    try {
	        url = new URL(BASE_URL + urlS);
	        input = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(input));

	        if ((line = br.readLine()) != null) {
	        	progSource += line;
	        }
	        
	        while ((line = br.readLine()) != null) {
	            progSource += "\n" + line;
	        }
	        
	        return progSource;
	    } catch (MalformedURLException m) {
	         m.printStackTrace();
	    } catch (IOException i) {
	         i.printStackTrace();
	    } finally {
	        try {
	            if (input != null) {
	            	input.close();
//	            	br.close();
	            }
	        } catch (IOException i) {
	            // all good
	        }
	    }
		
		return "";
	}
	
	public String getSource() {
		return source;
	}
}