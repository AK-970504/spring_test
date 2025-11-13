package jp.co.sss.spring_test.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.form.UserLoginForm;
import jp.co.sss.spring_test.repository.UserRepository;
import jp.co.sss.spring_test.util.PasswordsUtil;

@Controller
public class UserLoginController {
	@Autowired
	private UserRepository userRepository;
	//ログイン画面表示
	@GetMapping("/user/userLogin")
	public String showUserLogin(Model model) {
		model.addAttribute("userLoginForm", new UserLoginForm());
		return "user/userLogin";
	}
	//ログイン認証処理
	@PostMapping("/user/userLogin")
	public String doUserLogin(
		@Validated @ModelAttribute("userLoginForm") UserLoginForm form,
		BindingResult result,
		Model model,
		RedirectAttributes redirectAttributes,
		HttpServletRequest request) {
		//バリデーションエラー(アノテーション)
		if (result.hasErrors()) {
			return "/user/userLogin";
		}
		//メールアドレスでユーザ検索
		Optional<Users> usersOptional = userRepository.findByEmail(form.getEmail());
		//ユーザーが存在しない場合
		if (usersOptional.isEmpty()) {
			model.addAttribute("userLoginError", "メールアドレスまたはパスワードが正しくありません。");
			return "user/userLogin";
		}
		//OptionalからUsersを取得
		Users users = usersOptional.get();
		//パスワード認証(PBKDF2)
		boolean isValid = PasswordsUtil.verifyPasswords(form.getPasswords(), users.getPasswords());
		//パスワードが一致しない場合
		if (!isValid) {
			model.addAttribute("userLoginError", "メールアドレスまたはパスワードが正しくありません。");
			return "user/userLogin";
		}
		//認証成功
		redirectAttributes.addFlashAttribute("loginSuccess", "ログインに成功しました！");
		redirectAttributes.addFlashAttribute("userName", users.getUser_name());
		//セッションにユーザー情報を保存
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", users);
		return "redirect:/product/productTop";
	}
}