package jp.co.sss.spring_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.form.UserRegistrationForm;
import jp.co.sss.spring_test.repository.UserRepository;
import jp.co.sss.spring_test.util.PasswordsUtil;

@Service
public class UserRegistrationService {
	@Autowired
	private UserRepository userRegistrationRepository;
	/**
	 * ユーザー登録処理（パスワードをPBKDF2でハッシュ化して保存）
	 */
	public void registerUser(UserRegistrationForm form) {
		Users users = new Users();
		users.setUser_name(form.getUser_name());
		users.setEmail(form.getEmail());
		// パスワードをハッシュ化
		String hashedPasswords = PasswordsUtil.hashPasswords(form.getPasswords());
		users.setPasswords(hashedPasswords);
		//user_name_kanaを空文字で設定
		users.setUser_name_kana("");
		//phoneを空文字で設定
		users.setPhone("");
		//user_addressを空文字で設定
		users.setUser_address("");
		userRegistrationRepository.save(users);
	}
}