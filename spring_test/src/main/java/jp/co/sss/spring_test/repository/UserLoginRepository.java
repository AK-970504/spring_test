package jp.co.sss.spring_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.spring_test.entity.UserLogin;
import jp.co.sss.spring_test.entity.UserRegistration;

public interface UserLoginRepository extends JpaRepository<UserLogin, Integer> {
	Optional<UserRegistration> findByEmail(String email);
}