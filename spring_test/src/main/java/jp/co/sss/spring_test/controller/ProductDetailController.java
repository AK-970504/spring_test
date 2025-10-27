package jp.co.sss.spring_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductDetailController {
	//商品詳細画面表示
	@RequestMapping(path = "/product/productDetail", method = RequestMethod.GET)
	public String showProductDetail () {
		return "/product/productDetail";
	}
}