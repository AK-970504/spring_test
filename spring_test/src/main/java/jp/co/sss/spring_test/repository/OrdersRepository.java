package jp.co.sss.spring_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.spring_test.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	
}