package jp.co.sss.spring_test.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Reviews;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.CartsRepository;
import jp.co.sss.spring_test.repository.ProductDetailRepository;
import jp.co.sss.spring_test.service.ProductDetailService;
import jp.co.sss.spring_test.service.ReviewService;

@Controller
public class ProductDetailController {
	@Autowired
	private ProductDetailService productDetailService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ProductDetailRepository productDetailRepository;
	@Autowired
	private CartsRepository cartsRepository;
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
	@GetMapping(value = "/product/productDetail/image/{id}", produces = "image/jpeg")
	@ResponseBody
	public byte[] getDetailProductImage(@PathVariable Integer id) {
		Products product = productDetailService.findByIdWithCompany(id);
		if (product != null && product.getImg_path() != null) {
			 return product.getImg_path();
		}
		return null;
	}
	@PostMapping("/purchase/purchaseDetail")
	public String singlePurchase(
		@RequestParam("product_id") Integer productId,
		HttpSession session,
		RedirectAttributes redirectAttributes) {
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です");
			return "redirect:/user/userLogin";
		}
		Products product = productDetailRepository.findById(productId).orElse(null);
		if (product == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "商品が見つかりません");
			return "redirect:/product/list";
		}
		Carts cart = new Carts();
		cart.setUser(loginUser);
		cart.setProduct(product);
		cart.setQuantity(1);
		cart.setCreated_at(LocalDateTime.now());
		cart.setUpdated_at(LocalDateTime.now());
		cartsRepository.save(cart);
		return "redirect:/purchase/purchaseDetail/" + cart.getCart_id();
	}
}