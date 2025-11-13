package jp.co.sss.spring_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchaseDetailController {
	//購入詳細画面表示
	@GetMapping("/purchase/purchaseDetail")
	public String showPurchaseDetail() {
		return "/purchase/purchaseDetail";
	}
}