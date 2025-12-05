package jp.co.sss.spring_test.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeaderController {
	@GetMapping("/logout")
	public String Logout(
		HttpSession session
	) {
		session.invalidate();
		return "redirect:/user/userLogin";
	}
}