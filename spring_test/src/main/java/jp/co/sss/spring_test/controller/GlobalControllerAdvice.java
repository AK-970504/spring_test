package jp.co.sss.spring_test.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.spring_test.entity.Users;

@ControllerAdvice
public class GlobalControllerAdvice {
	@ModelAttribute("headerUserName")
	public String addUserNameToModel(HttpSession session) {
		Users user = (Users) session.getAttribute("loginUser");
		if (user == null) {
			return "";
		}
		return user.getUser_name();
	}
}