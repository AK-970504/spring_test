package jp.co.sss.spring_test.controller;

import java.util.Base64;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.CartsRepository;

@Controller
public class PurchaseCompletionController {
	@Autowired
	private CartsRepository cartsRepository;
	@GetMapping("/purchase/purchaseCompletion/{cartId}")
	public String showPurchaseCompletion(
		@PathVariable("cartId") Integer cartId,
		HttpSession session,
		Model model) {
		if(cartId == null) {
			return "redirect:/";
		}
		Carts cart = cartsRepository.findByIdWithUserAndProduct(cartId).orElse(null);
		if(cart == null) {
			return "redirect:/";
		}
		Users user = cart.getUser();
		Products product = cart.getProduct();
		Integer quantity = cart.getQuantity();
		String address = "";
		String building = "";
		if(user.getUser_address() != null) {
			String[] parts = user.getUser_address().split(" ", 2);
			address = parts[0];
			if(parts.length > 1) building = parts[1];
		}
		Integer totalPrice = product.getTax_price() * quantity;
		byte[] imgBytes = product.getImg_path();
		String base64Img = "";
		if(imgBytes != null && imgBytes.length > 0) {
			base64Img = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imgBytes);
		}
		model.addAttribute("quantity", quantity);
		model.addAttribute("address", address + " " + building);
		model.addAttribute("productName", product.getProduct_name());
		model.addAttribute("productImg", base64Img);
		model.addAttribute("totalPrice", totalPrice);
		return "/purchase/purchaseCompletion";
	}
}