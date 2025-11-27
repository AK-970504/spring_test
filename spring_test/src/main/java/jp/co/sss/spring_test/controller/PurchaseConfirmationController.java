package jp.co.sss.spring_test.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.CartsRepository;

@Controller
public class PurchaseConfirmationController {
	@Autowired
	private CartsRepository cartsRepository;
	@GetMapping("/purchase/purchaseConfirmation/{cartId}")
	public String showPurchaseConfirmation(
		HttpSession session,
		Model model) {
		Integer cartId = (Integer) session.getAttribute("purchaseCartId");
		if (cartId == null) {
			return "redirect:/";
		}
		Carts cart = cartsRepository.findByIdWithUserAndProduct(cartId).orElse(null);
		if (cart == null) {
			return "redirect:/";
		}
		Users user = cart.getUser();
		Products product = cart.getProduct();
		Integer quantity = cart.getQuantity();
		String address = "";
		String building = "";
		if (user.getUser_address() != null) {
			String[] parts = user.getUser_address().split(" ", 2);
			address = parts[0];
			if (parts.length > 1) building = parts[1];
		}
		Integer totalPrice = product.getTax_price() * quantity;
		model.addAttribute("cart", cart);
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		model.addAttribute("building", building);
		model.addAttribute("product", product);
		model.addAttribute("quantity", quantity);
		model.addAttribute("totalPrice", totalPrice);
		return "/purchase/purchaseConfirmation";
	}
}