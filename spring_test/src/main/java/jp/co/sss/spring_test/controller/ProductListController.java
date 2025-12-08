package jp.co.sss.spring_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.service.ProductListService;

@Controller
public class ProductListController {
	@Autowired                                                                                                                                      
	private ProductListService productListService;
	@GetMapping("/product/productList")
	public String showProductList(Model model) {
		List<Products> productList = productListService.findAll();
		model.addAttribute("products", productList);
		return "product/productList";
	}
}