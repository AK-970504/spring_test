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
	@Query("SELECT p FROM Products p JOIN FETCH p.company WHERE p.product_name LIKE %:name% ORDER BY p.product_id ASC")
	List<Products> findByNameOrderById(String name);
	@Query("SELECT p FROM Products p JOIN FETCH p.company WHERE p.product_name LIKE %:name% ORDER BY p.category.category_id ASC")
	List<Products> findByNameOrderByCategory(String name);
	@Query("SELECT p FROM Products p JOIN FETCH p.company WHERE p.product_name LIKE %:name% ORDER BY p.company.company_id ASC")
	List<Products> findByNameOrderByCompany(String name);
	@Query("SELECT p FROM Products p JOIN FETCH p.company WHERE p.product_name LIKE %:name% ORDER BY p.tax_price ASC")
	List<Products> findByNameOrderByPriceAsc(String name);
	@Query("SELECT p FROM Products p JOIN FETCH p.company WHERE p.product_name LIKE %:name% ORDER BY p.tax_price DESC")
	List<Products> findByNameOrderByPriceDesc(String name);
	@Query("SELECT p FROM Products p JOIN FETCH p.company WHERE p.product_name LIKE %:name% ORDER BY p.created_at ASC")
	List<Products> findByNameOrderByNew(String name);
}