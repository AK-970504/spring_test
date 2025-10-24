package jp.co.sss.spring_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.spring_test.entity.UserRegistration;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Integer> {
	//メールアドレス重複チェック用検索メソッド
	UserRegistration findByEmail(String email);
}