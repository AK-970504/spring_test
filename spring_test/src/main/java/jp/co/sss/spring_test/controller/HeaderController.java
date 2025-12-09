package jp.co.sss.spring_test.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.service.HeaderService;

@Controller
public class HeaderController {
	@Autowired
	private HeaderService headerService;
	@ModelAttribute("headerUserName")
	public String addUserNameToModel(HttpSession session) {
		Users user = (Users) session.getAttribute("loginUser");
		if (user == null) {
			return "";
		}
		return user.getUser_name();
	}
	@GetMapping("/logout")
	public String Logout(
		HttpSession session
	) {
		session.invalidate();
		return "redirect:/user/userLogin";
	}
	@GetMapping("/product/productList/search")
	public String search(
		@RequestParam String name,
		@RequestParam String sort,
		Model model
	){
		List<Products> list = headerService.search(name, sort);
		model.addAttribute("products", list);
		return "product/productList";
	}
}