package jp.co.sss.spring_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Integer> {
	Optional<Carts> findByUserAndProduct(Users user, Products product);
	@Query("SELECT c FROM Carts c WHERE c.user = :user AND c.product.product_id = :productId")
	Optional<Carts> findByUserAndProductId(@Param("user") Users user, @Param("productId") Integer productId);
	@Query("SELECT c FROM Carts c JOIN FETCH c.user JOIN FETCH c.product WHERE c.cart_id = :cartId")
	Optional<Carts> findByIdWithUserAndProduct(@Param("cartId") Integer cartId);
}