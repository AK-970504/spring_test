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
			// product が null のときのテンプレート表示を想定
			model.addAttribute("product", null);
			model.addAttribute("quantity", 0);
			model.addAttribute("totalPrice", 0);
			model.addAttribute("cartId", cartId);
			return "cart/cartDetail";
		}
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
		@RequestParam(required = false) Integer removeQuantity,
		@SessionAttribute(value = "loginUser", required = false) Users user,
		Model model) {
		if (user == null) {
			return "redirect:/user/userLogin";
		}
		if (removeQuantity == null) {
			// 選択されていない場合はフラッシュメッセージ等にするのがベストですが、
			// 簡単にクエリパラメータでメッセージを渡して GET 側で表示する方法を取ります。
			return "redirect:/cart/cartDetail/" + cartId + "?msg=selectQuantity";
		}
		cartService.reduceQuantity(user, productId, removeQuantity);
		// POST-Redirect-GET: 最新の状態を表示させる
		return "redirect:/cart/cartDetail/" + cartId;
	}
}