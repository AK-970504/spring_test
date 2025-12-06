package jp.co.sss.spring_test.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.spring_test.dto.ProductDto;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.service.HeaderService;

@RestController
public class HeaderController {
	@GetMapping("/logout")
	public String Logout(
		HttpSession session
	) {
		session.invalidate();
		return "redirect:/user/userLogin";
	}
	@Autowired
	private HeaderService headerService;
	@GetMapping("/product/productList/search")
	public List<ProductDto> search(
		@RequestParam String name,
		@RequestParam String sort
	){
		List<Products> list = headerService.search(name, sort);
		return list.stream()
			.map(ProductDto::new)
			.toList();
	}
}