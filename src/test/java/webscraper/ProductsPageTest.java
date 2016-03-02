package webscraper;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import webscraper.pageParsers.ProductsPage;

public class ProductsPageTest {

	@Test
	public void testParsingProductPage() throws IOException {
		String contents = new String(readAllBytes(get("src/test/resources/productsPage.html")));		
		ProductsPage productsPage = new ProductsPage(contents);
		
		List<String> productUrls = productsPage.getProductUrls();		
		assertEquals(14, productUrls.size());
	}
}
