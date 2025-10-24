package jp.co.sss.spring_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductTopController {
	@RequestMapping(path = "/product/productTop", method = RequestMethod.GET)
	public String showProductTop() {
		return "/product/productTop";
	}
}