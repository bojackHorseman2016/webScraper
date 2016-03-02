package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import webscraper.model.ProductDetails;
import webscraper.model.ProductPageDetails;
import webscraper.pageParsers.ProductSummaryPage;
import webscraper.pageParsers.ProductsPage;

public class Process {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Utilities.outputOff(); // Avoid spamming any FX or other output until we have the result
			
		// Start FX engine
		Thread t = new Thread( ()-> WebScrapingEngine.launchIt(args) );
		t.start();
		Thread.sleep(1000);	// We need FX application to be ready before we use it, this is the quick and dirty solution, we should be able to do better

		String initialUrl = "http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?listView=true&orderBy=FAVOURITES_FIRST&parent_category_rn=12518&top_category=12518&langId=44&beginIndex=0&pageSize=20&catalogId=10137&searchTerm=&categoryId=185749&listId=&storeId=10151&promotionId=#langId=44&storeId=10151&catalogId=10137&categoryId=185749&parent_category_rn=12518&top_category=12518&pageSize=20&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true";
		
		// Get the products page, we don't get the result until it is fully loaded (until the product are actually in the DOM)
		String result = WebScrapingEngine.getUrl(initialUrl, ProductsPage::pageFullyLoaded);

		ProductsPage productsPage = new ProductsPage(result);		
		List<ProductDetails> productDetails = new ArrayList<>();
		
		// Get each product summary page
		for (String productUrl : productsPage.getProductUrls()) {
			String pageContents = WebScrapingEngine.getUrl(productUrl, ProductSummaryPage::pageFullyLoaded);
			ProductSummaryPage page = new ProductSummaryPage(pageContents);
			productDetails.add(page.getDetails());
		}
	    
		double sum = productDetails.stream().mapToDouble(x -> x.unit_price).sum(); // sum the unit prices of all products
		
		// Construct the output object
		ProductPageDetails productPageDetails = new ProductPageDetails();
		productPageDetails.results = productDetails;		
		productPageDetails.total = sum;
		
		// Finally output the result as json
		Utilities.outputOn();
		System.out.println(JsonSerializer.toJson(productPageDetails));
		
		// We actually need this to kill the FX application
	    System.exit(0);
	}
}
