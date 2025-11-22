package jp.co.sss.spring_test.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.CartsRepository;
import jp.co.sss.spring_test.repository.ProductDetailRepository;
import jp.co.sss.spring_test.repository.UserRepository;

@Controller
public class PurchaseDetailController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductDetailRepository productDetailRepository;
	@Autowired
	private CartsRepository cartsRepository;
	@GetMapping("/purchase/purchaseDetail")
	public String showPurchaseDetail() {
		return "purchase/purchaseDetail";
	}
	@PostMapping("/purchase/saveAddress")
	public String saveAddress(
		@RequestParam String addressOption,
		@RequestParam(required = false) String address1,
		@RequestParam(required = false) String building1,
		@RequestParam(required = false) String address2,
		@RequestParam(required = false) String building2,
		HttpSession session) {
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/user/userLogin";
		}
		String fullAddress = "";
		if ("1".equals(addressOption)) {
			fullAddress = address1;
			if (building1 != null && !building1.isEmpty()) fullAddress += " " + building1;
		} else if ("2".equals(addressOption)) {
			fullAddress = address2;
			if (building2 != null && !building2.isEmpty()) fullAddress += " " + building2;
		}
		loginUser.setUser_address(fullAddress);
		userRepository.save(loginUser);
		session.setAttribute("loginUser", loginUser);
		return "redirect:/purchase/purchaseCompletion";
	}
	@PostMapping("/purchase/single")
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
		return "redirect:/purchase/purchaseDetail?cartId=" + cart.getCart_id();
	}
}
