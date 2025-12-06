package jp.co.sss.spring_test.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Reviews;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.service.ProductDetailService;
import jp.co.sss.spring_test.service.ReviewService;

@Controller
public class ReviewPostController {
	@Autowired
	private ProductDetailService productDetailService;
	@Autowired
	private ReviewService reviewService;
	@GetMapping("/review/reviewPost/{productId}")
	public String showReviewPost(
		@PathVariable("productId") Integer productId,
		Model model) {
		Products product = productDetailService.findByIdWithCompany(productId);
		model.addAttribute("product", product);
		return "/review/reviewPost";
	}
	@PostMapping("/review/reviewPost/{productId}")
	public String postReview(
		@PathVariable("productId") Integer productId,
		@RequestParam(name="dummy_user_name", required=false) String dummyUserName,
		@RequestParam(name="comment") String comment,
		@RequestParam(name="rating") Integer rating,
		@RequestParam(name="review_img_path", required=false) MultipartFile file,
		HttpSession session
    ) throws IOException {
		Reviews review = new Reviews();
		review.setDummy_user_name(dummyUserName);
		review.setComment(comment);
		review.setRating(rating);
		if (file != null && !file.isEmpty()) {
			review.setReview_img_path(file.getBytes());
		}
		Users loginUser = (Users) session.getAttribute("loginUser");
		review.setUsers(loginUser);
		if (loginUser == null) {
			return "redirect:/user/userLogin";
		}
		Products product = productDetailService.findByIdWithCompany(productId);
		review.setProducts(product);
		LocalDateTime now = LocalDateTime.now();
		review.setCreatedAt(now);
		review.setUpdatedAt(now);
		reviewService.saveReview(review);
		return "redirect:/product/productList";
	}
}