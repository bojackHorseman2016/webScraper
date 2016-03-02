package webscraper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import webscraper.model.ProductPageDetails;

public class JsonSerializer {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(ProductPageDetails pp) throws JsonProcessingException {
		return mapper.writeValueAsString(pp);
	}
}
