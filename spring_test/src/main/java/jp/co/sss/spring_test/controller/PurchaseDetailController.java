package jp.co.sss.spring_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.service.ProductDetailService;

@Controller
public class PurchaseDetailController {
	@Autowired
	private ProductDetailService productDetailService;
	//購入詳細画面表示
	@GetMapping("/purchase/purchaseDetail/{id}")
	public String showPurchaseDetail(
			@PathVariable("id") Integer id,
			Model model) {
		//商品情報を取得
		Products product = productDetailService.findByIdWithCompany(id);
		//モデルにセット
		model.addAttribute("product", product);
		return "/purchase/purchaseDetail";
	}
}