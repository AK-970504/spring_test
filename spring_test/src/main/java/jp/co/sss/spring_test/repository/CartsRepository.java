package jp.co.sss.spring_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Users;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Integer> {
	Optional<Carts> findByUserAndProduct(Users user, Products product);
}