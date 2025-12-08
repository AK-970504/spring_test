package jp.co.sss.spring_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.spring_test.entity.SalesItems;

public interface SalesItemsRepository extends JpaRepository<SalesItems, Integer> {

}