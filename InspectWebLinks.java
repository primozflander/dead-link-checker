package automation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InspectWebLinks {

	static int validLinks = 0;
	static int brokenLinks = 0;
	static int emptyLinks = 0;
	static int skippedLinks = 0;
	static String reportData = "";

	public static void main(String[] args) throws IOException {
		
		inspect("https://www.example.com");
		generate_report(reportData,"C:\\Users\\HP\\Desktop\\report.txt");
		
	}
	
	
	private static void generate_report(String reportData, String reportPath) throws IOException {
		
		FileWriter report = new FileWriter(reportPath);
		report.write(reportData);
		report.close();
		
	}

	
	private static int check_link(String url) {
		
		if(url == null || url.isEmpty()){	
			return 0;
		}
		Response response;
		try {
			response = Jsoup.connect(url).execute();
			if(response.statusCode() == 200) {
				return 1;
			}
			else {
				return 0;
			}
		} catch (IOException e) {
			return 0;
		}
	}
	
	private static Set<String> get_links_on_page(String url) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
		Elements links = doc.select("a");
		
		Set<String> found_urls = new HashSet<String>();
		
		for (Element link: links) {
			String sub_url = link.attr("abs:href");
			
			if (sub_url == null || sub_url.isEmpty()) {
				//System.out.println("\n Url is empty " + link.outerHtml() + " at " + url);
				reportData += "\n Url is empty " + link.outerHtml() + " at " + url;
				emptyLinks += 1;
			}
			else {
				found_urls.add(sub_url);
			}
			
		}
		return found_urls;
	}
	
	private static void inspect(String start_url) throws IOException {
		
		Set<String> visited = new HashSet<String>();
		Stack<String> to_visit = new Stack<String>();
		to_visit.push(start_url);
		

		while (!to_visit.isEmpty()) {
			String current_link = to_visit.pop();
			if (!visited.contains(current_link) && current_link.startsWith(start_url)) {
				System.out.print(".");
				
				int response = check_link(current_link);
				if (response == 1) {
					validLinks += 1;
					Set<String> found_links = get_links_on_page(current_link);
					for(String new_link: found_links) {
						if (!visited.contains(new_link)) {
							to_visit.push(new_link);
						}
					}
				} else if (response == 0) {
					brokenLinks += 1;
					System.out.println("\n Url is broken " + current_link);
					reportData += "\n Url is broken " + current_link;
				}
				visited.add(current_link);
			}
			else {
				skippedLinks += 1;
				//System.out.println("\n Url skipped " + current_link);
				reportData += "\n Url skipped " + current_link;
			}
		}
		System.out.format("Finished! (%2d empty) (%2d skipped) (%2d broken) (%2d valid)", emptyLinks, skippedLinks, brokenLinks, validLinks);
	}
	
	
}