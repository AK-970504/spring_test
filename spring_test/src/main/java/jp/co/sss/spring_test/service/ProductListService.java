package jp.co.sss.spring_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.repository.ProductListRepository;

@Service
public class ProductListService {
	@Autowired
	private ProductListRepository productListRepository;
	public List<Products> findAll() {
		return productListRepository.findAllWithCompany();
	}
	public Products findById(Integer id) {
		return productListRepository.findById(id).orElse(null);
	}
}