package jp.co.sss.spring_test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.sss.spring_test.entity.Products;

@Repository
public interface ProductListRepository extends JpaRepository<Products, Integer> {
	@Query("SELECT p FROM Products p JOIN FETCH p.company ORDER BY p.product_id ASC")
	List<Products> findAllWithCompany();
}