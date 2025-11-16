package jp.co.sss.spring_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Reviews;
import jp.co.sss.spring_test.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	public List<Reviews> getReviewsByProduct(Products product) {
		return reviewRepository.findByProductsOrderByCreatedAtDesc(product);
	}
	public Reviews saveReview(Reviews review) {
		if (review.getReview_img_path() == null) {
			review.setReview_img_path(new byte[0]);
		}
		return reviewRepository.save(review);
	}
}