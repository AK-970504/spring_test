package jp.co.sss.spring_test.controller;

import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.ProductDetailRepository;
import jp.co.sss.spring_test.service.CartService;

@Controller
public class CartAdditionController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductDetailRepository productDetailRepository;
	//カート追加完了画面表示
	@GetMapping("/cart/cartAddition/{cartId}")
	public String showCartAddition(
		@PathVariable("cartId") Integer cartId,
		Model model
	) {
		//cartId から商品情報を取得
		Map<String, Object> cartDetail = cartService.findCartDetailByCartId(cartId);
	    if (cartDetail == null) {
	        model.addAttribute("errorMessage", "該当する商品が見つかりません。");
	        return "cart/cartAddition";
	    }
	    model.addAttribute("cartId", cartId);
	    model.addAttribute("productDetail", cartDetail.get("product"));
	    model.addAttribute("quantity", cartDetail.get("quantity"));
	    model.addAttribute("totalPrice", cartDetail.get("totalPrice"));
	    return "cart/cartAddition";
	}
	//カート追加処理
	@PostMapping("/cart/add")
	public String addCart(
		@RequestParam("product_id") Integer productId,
		@RequestParam("quantity") Integer quantity,
		HttpSession session,
		RedirectAttributes redirectAttributes
	) {
		//セッションからuser_idを取得
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) {
			redirectAttributes.addFlashAttribute("cartError", "ログインして下さい。");
			return "redirect:/user/userLogin";
		}
		//カート追加処理してcartIdを受け取る
		Integer cartId = cartService.addToCart(loginUser, productId, quantity);
		if (cartId == null) {
			redirectAttributes.addFlashAttribute("cartError", "カート追加に失敗しました。");
			return "redirect:/product/productDetail/" + productId;
		}
		redirectAttributes.addFlashAttribute("cartSuccess", "カートに追加しました。");
		return "redirect:/cart/cartAddition/" + cartId;
	}
	//商品画像を表示
	@GetMapping("/cart/image/{productId}")
	public ResponseEntity<byte[]> getProdoductImage(@PathVariable("productId") Integer productId) {
		Optional<Products> optionalProduct = productDetailRepository.findById(productId);
		if (optionalProduct.isPresent()) {
			Products product = optionalProduct.get();
			byte[] imageBytes = product.getImg_path();
			if (imageBytes != null && imageBytes.length > 0) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.IMAGE_JPEG);
				return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}