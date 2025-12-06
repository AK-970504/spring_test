
package jp.co.sss.spring_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.repository.ProductListRepository;

@Service
public class HeaderService {
	@Autowired
	private ProductListRepository repository;
	public List<Products> search(String name, String sort) {
		switch (sort) {
		case "fashion":
			return repository.findByNameOrderById(name);
		case "category":
			return repository.findByNameOrderByCategory(name);
		case "company":
			return repository.findByNameOrderByCompany(name);
		case "priceAsc":
			return repository.findByNameOrderByPriceAsc(name);
		case "priceDesc":
			return repository.findByNameOrderByPriceDesc(name);
		case "new":
			return repository.findByNameOrderByNew(name);
		default:
			return repository.findByNameOrderById(name);
		}
	}
}