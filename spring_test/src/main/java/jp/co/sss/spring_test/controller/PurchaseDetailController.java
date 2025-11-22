package jp.co.sss.spring_test.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.UserRepository;

@Controller
public class PurchaseDetailController {
	@Autowired
	private UserRepository userRepository;
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
}
