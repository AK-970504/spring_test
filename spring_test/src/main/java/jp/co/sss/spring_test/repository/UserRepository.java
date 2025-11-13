package jp.co.sss.spring_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.spring_test.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByEmail(String email);
}