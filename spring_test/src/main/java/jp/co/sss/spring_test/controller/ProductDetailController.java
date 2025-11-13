package jp.co.sss.spring_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Reviews;
import jp.co.sss.spring_test.service.ProductDetailService;
import jp.co.sss.spring_test.service.ReviewService;

@Controller
public class ProductDetailController {
	@Autowired
	private ProductDetailService productDetailService;
	@Autowired
	private ReviewService reviewService;
	//商品詳細画面表示
	@GetMapping("/product/productDetail/{id}")
	public String showProductDetail(@PathVariable("id") Integer id, Model model) {
		//商品情報を取得
		Products products = productDetailService.findByIdWithCompany(id);
		//口コミ情報を取得
		List<Reviews> reviews = reviewService.getReviewsByProduct(products);
		//モデルにデータを追加
		model.addAttribute("productDetail", products);
		model.addAttribute("reviews", reviews);
		return "product/productDetail";
	}
}