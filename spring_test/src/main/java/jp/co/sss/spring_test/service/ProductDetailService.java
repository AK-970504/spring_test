package jp.co.sss.spring_test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.repository.ProductDetailRepository;

@Service
public class ProductDetailService {
	@Autowired
	private ProductDetailRepository ProductDetailRepository;
	public Products findByIdWithCompany(Integer id) {
		Optional<Products> productOpt = ProductDetailRepository.findByIdWithCompany(id);
		if (productOpt.isEmpty()) {
			throw new IllegalArgumentException("Invalid product Id: " + id);
		}
		return productOpt.get();
	}
}