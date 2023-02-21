package onlineshop.storage.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import onlineshop.enteties.Product;
import onlineshop.enteties.impl.DefaultProduct;
import onlineshop.storage.ProductStoringService;

public class DefaultProductStoringService implements ProductStoringService {	
	
	private static final String PRODUCTS_INFO_STORAGE = "products.csv";
	private static final String CURRENT_TASK_RESOURCE_FOLDER = "onlinestore";
	private static final String RESOURCES_FOLDER = "resources";
	private static final int PRODUCT_PRICE_INDEX = 3;
	private static final int PRODUCT_CATEGORY_INDEX = 2;
	private static final int PRODUCT_NAME_INDEX = 1;
	private static final int PRODUCT_ID_INDEX = 0;
	
	@Override
	public List<Product> loadProducts() {
		try(var stream = Files.lines(Paths.get(RESOURCES_FOLDER, CURRENT_TASK_RESOURCE_FOLDER, PRODUCTS_INFO_STORAGE))) {
			return stream.filter(Objects::nonNull)
					.filter(line -> !line.isEmpty())
					.map(line -> {
						String[] products = line.split(",");
						return new DefaultProduct(Integer.valueOf(products[PRODUCT_ID_INDEX]), 
								products[PRODUCT_NAME_INDEX], products[PRODUCT_CATEGORY_INDEX], 
								Double.valueOf(products[PRODUCT_PRICE_INDEX]));
					}).collect(Collectors.toList());
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
	}
}
