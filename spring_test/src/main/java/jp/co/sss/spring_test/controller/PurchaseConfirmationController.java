package jp.co.sss.spring_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchaseConfirmationController {
	//購入詳細画面表示
	@GetMapping("/purchase/purchaseConfirmation")
	public String showPurchaseConfirmation() {
		return "/purchase/purchaseConfirmation";
	}
}