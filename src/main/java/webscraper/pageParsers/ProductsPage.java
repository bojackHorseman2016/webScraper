package webscraper.pageParsers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProductsPage {

	private List<String> productUrls = new ArrayList<>();

	public ProductsPage(String rawHtml) {
		Document doc = Jsoup.parse(rawHtml);
		productUrls = getProductUrls(doc);
	}

	public List<String> getProductUrls(Document doc) {
		List<String> result = new ArrayList<>();
		Elements elements = doc.select("div.productInfo > h3 > a");
		for (Element element : elements) {
			result.add(element.attr("href"));
		}
		return result;
	}
	
	public List<String> getProductUrls() {
		return productUrls;
	}
	
	public static boolean pageFullyLoaded(String str) {
		Document doc = Jsoup.parse(str);
		Elements elements = doc.select("div.productInfo");
		return elements.size() > 0;
	}
}
