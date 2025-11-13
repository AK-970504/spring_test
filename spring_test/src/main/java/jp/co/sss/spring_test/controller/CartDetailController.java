package jp.co.sss.spring_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartDetailController {
	//カート詳細画面表示
	@GetMapping("/cart/cartDetail")
	public String showCartDetail() {
		return "/cart/cartDetail";
	}
}