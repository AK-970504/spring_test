package jp.co.sss.spring_test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.service.CartService;

@Controller
public class CartDetailController {
	@Autowired
	private CartService cartService;
	@GetMapping("/cart/cartDetail/{cartId}")
	public String showCartDetail(
		@PathVariable("cartId") Integer cartId,
		@SessionAttribute(value = "loginUser", required = false) Users user,
		Model model) {
		if (user == null) {
			return "redirect:/user/userLogin";
		}
		Map<String, Object> cartDetail = cartService.findCartDetailByCartId(cartId);
		if (cartDetail == null) {
			model.addAttribute("errorMessage", "カート情報が見つかりません。");
			return "/cart/cartDetail";
		}
		//商品情報取得
		Products product = (Products) cartDetail.get("product");
		model.addAttribute("product", product);
		model.addAttribute("quantity", cartDetail.get("quantity"));
		model.addAttribute("totalPrice", cartDetail.get("totalPrice"));
		model.addAttribute("cartId", cartId);
		return "cart/cartDetail";
	}
	@PostMapping("/cart/cartDetail/{cartId}")
	public String deleteFormCart(
		@PathVariable("cartId") Integer cartId,
		@RequestParam Integer productId,
		@RequestParam Integer removeQuantity,
		@SessionAttribute("loginUser") Users user,
		Model model) {
		if (user == null) {
			return "redirect:/user/userLogin";
		}
		cartService.reduceQuantity(user, productId, removeQuantity);
		Map<String, Object> cartDetail = cartService.findCartDetailByCartId(cartId);
		if (cartDetail == null) {
			model.addAttribute("message", "カートから削除されました");
			return "cart/cartDetail";
		}
		Products product = (Products) cartDetail.get("product");
		model.addAttribute("product", product);
		model.addAttribute("quantity", cartDetail.get("quantity"));
		model.addAttribute("totalPrice", cartDetail.get("totalPrice"));
		model.addAttribute("cartId", cartId);
		return "cart/cartDetail";
	}
}