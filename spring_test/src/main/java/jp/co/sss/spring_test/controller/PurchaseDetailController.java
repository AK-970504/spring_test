package jp.co.sss.spring_test.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.CartsRepository;
import jp.co.sss.spring_test.repository.UserRepository;

@Controller
public class PurchaseDetailController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartsRepository cartsRepository;
	@GetMapping("/purchase/purchaseDetail/{cartId}")
	public String showPurchaseDetail(
		@PathVariable("cartId") Integer cartId,
		Model model,
		HttpSession session) {
		session.setAttribute("purchaseCartId", cartId);
		Carts cart = cartsRepository.findById(cartId).orElse(null);
		if (cart == null) {
			return "redirect:/";
		}
		model.addAttribute("cart", cart);
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser != null && loginUser.getUser_address() != null) {
			String[] parts = loginUser.getUser_address().split(" ", 2);
			model.addAttribute("address", parts[0]);
			model.addAttribute("building", parts.length > 1 ? parts[1] : "");
		}
		String paymentNumber = (String) session.getAttribute("paymentNumber");
		String paymentExpire = (String) session.getAttribute("paymentExpire");
		model.addAttribute("paymentNumber", paymentNumber);
		model.addAttribute("paymentExpire", paymentExpire);
		String paymentOption = (String) session.getAttribute("paymentOption");
		model.addAttribute("paymentOption", paymentOption != null ? paymentOption : "1");
		return "purchase/purchaseDetail";
	}
	@PostMapping("/purchase/saveAddress")
	public String saveAddress(
		@RequestParam String addressOption,
		@RequestParam(required = false) String address1,
		@RequestParam(required = false) String building1,
		@RequestParam(required = false) String address2,
		@RequestParam(required = false) String building2,
		@RequestParam(required = false) String paymentNumber1,
		@RequestParam(required = false) String paymentExpire1,
		@RequestParam(required = false) String paymentNumber2,
		@RequestParam(required = false) String paymentExpire2,
		HttpSession session) {
		Users loginUser = (Users) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/user/userLogin";
		}
		Integer cartId = (Integer) session.getAttribute("purchaseCartId");
		if (cartId == null) {
			return "redirect:/";
		}
		String paymentNumber = paymentNumber1 != null ? paymentNumber1 : paymentNumber2;
		String paymentExpire = paymentExpire1 != null ? paymentExpire1 : paymentExpire2;
		session.setAttribute("paymentNumber", paymentNumber);
		session.setAttribute("paymentExpire", paymentExpire);
		session.setAttribute("paymentOption", addressOption);
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
		return "redirect:/purchase/purchaseConfirmation/" + cartId;
	}
}