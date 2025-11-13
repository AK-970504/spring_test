package jp.co.sss.spring_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewPostController {
	//口コミ投稿画面表示
	@GetMapping("/review/reviewPost")
	public String showReviewPost() {
		return "/review/reviewPost";
	}
}