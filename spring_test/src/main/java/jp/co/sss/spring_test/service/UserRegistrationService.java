package jp.co.sss.spring_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.UserRegistration;
import jp.co.sss.spring_test.form.UserRegistrationForm;
import jp.co.sss.spring_test.repository.UserRegistrationRepository;
import jp.co.sss.spring_test.util.PasswordsUtil;

@Service
public class UserRegistrationService {
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	/**
	 * ユーザー登録処理（パスワードをPBKDF2でハッシュ化して保存）
	 */
	public void registerUser(UserRegistrationForm form) {
		UserRegistration user = new UserRegistration();
		user.setUser_name(form.getUser_name());
		user.setEmail(form.getEmail());
		// パスワードをハッシュ化
		String hashedPasswords = PasswordsUtil.hashPasswords(form.getPasswords());
		user.setPasswords(hashedPasswords);
		userRegistrationRepository.save(user);
	}
}