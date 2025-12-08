package jp.co.sss.spring_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.service.ProductTopService;

@Controller
public class ProductTopController {
	@Autowired                                                                                                                                      
	private ProductTopService productTopService;
	//商品トップ画面表示
	@GetMapping("/product/productTop")
	public String showProductTop(Model model) {
		List<Products> productList = productTopService.findAll();
		model.addAttribute("products", productList);
		return "product/productTop";
	}
}