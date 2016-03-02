package webscraper;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import webscraper.model.ProductDetails;
import webscraper.pageParsers.ProductSummaryPage;

public class ProductSummaryPageTest {
	@Test
	public void testParseProductSummaryPage() throws IOException {
		String contents = new String(readAllBytes(get("src/test/resources/productSummaryPage.html")));	
		ProductSummaryPage productSummaryPage = new ProductSummaryPage(contents);
		
		ProductDetails details = productSummaryPage.getDetails();
		assertEquals("Sainsbury's Avocado Ripe & Ready XL Loose 300g", details.title);
		assertEquals("Avocados", details.description);
	}
}
