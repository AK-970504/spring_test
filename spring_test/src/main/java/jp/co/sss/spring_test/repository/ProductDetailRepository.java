package jp.co.sss.spring_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.sss.spring_test.entity.Products;

@Repository
public interface ProductDetailRepository extends JpaRepository<Products, Integer> {
	//companyを一緒に読込む
	@Query("SELECT p FROM Products p JOIN FETCH p.company WHERE p.product_id = :id")
	Optional<Products> findByIdWithCompany(@Param("id") Integer id);
}