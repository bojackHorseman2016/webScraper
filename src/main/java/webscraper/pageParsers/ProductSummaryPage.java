package webscraper.pageParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import webscraper.model.ProductDetails;

public class ProductSummaryPage {
	private int pageSize;
	private String title;
	private String description;
	private double unitPrice;

	public ProductSummaryPage(String rawHtml) {
		pageSize = rawHtml.length();
		
		Document doc = Jsoup.parse(rawHtml);
		title = getTitle(doc);
		description = getDescription(doc);
		unitPrice = getPricePerUnit(doc);
	}

	private String getTitle(Document doc) {
		Elements elements = doc.select("div.productSummary > div.productTitleDescriptionContainer > h1");
	    return (elements.size() > 0) ? elements.first().text() : "";
	}
	
	private String getDescription(Document doc) {
		Elements elements = doc.select("div.productText");
	    return (elements.size() > 0) ? elements.first().text() : "";
	}

	private double getPricePerUnit(Document doc) {
		Elements elements = doc.select("p.pricePerUnit");
	    String unitPriceText = elements.first().text();
	    return Double.parseDouble(unitPriceText.substring(1, unitPriceText.indexOf('/')));    
	}
	
	public static boolean pageFullyLoaded(String str) {
		Document doc = Jsoup.parse(str);
		Elements elements = doc.select("div.productSummary");
		return elements.size() > 0;
	}
	
	public ProductDetails getDetails() {
		ProductDetails details = new ProductDetails();
		details.title = title;
		details.description = description;
		details.size = String.format("%.1f%s", (pageSize / 1000.0), "kb");
		details.unit_price = unitPrice;
		
		return details;
	}
}
