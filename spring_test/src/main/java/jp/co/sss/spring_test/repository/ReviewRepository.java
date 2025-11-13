package jp.co.sss.spring_test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, Integer> {
	List<Reviews> findByProductsOrderByCreatedAtDesc(Products product);
}