package jp.co.sss.spring_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.spring_test.entity.ProductList;

@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Integer> {
	
}