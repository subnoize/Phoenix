package com.subnoize.phoenix.web.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subnoize.phoenix.aws.dynamodb.Product;
import com.subnoize.phoenix.aws.dynamodb.ProductDAO;

@RestController
public class ProductController {

	@Autowired
	private ProductDAO productDao;
	
	@RequestMapping("/product")
	public String createProduct(Product product) {
		
		//productDao.create(product);
	//	return product;
		
		return "hello";
	}
	
}
