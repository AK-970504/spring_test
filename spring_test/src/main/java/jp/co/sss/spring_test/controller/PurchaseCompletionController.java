package jp.co.sss.spring_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchaseCompletionController {
	//購入完了画面表示
	@GetMapping("/purchase/purchaseCompletion")
	public String showPurchaseCompletion() {
		return "/purchase/purchaseCompletion";
	}
}