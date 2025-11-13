package jp.co.sss.spring_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.repository.ProductTopRepository;

@Service
public class ProductTopService {
	@Autowired
	private ProductTopRepository productTopRepository;
	public List<Products> findAll() {
		return productTopRepository.findAll();
	}
	public Products findById(Integer id) {
		return productTopRepository.findById(id).orElse(null);
	}
}