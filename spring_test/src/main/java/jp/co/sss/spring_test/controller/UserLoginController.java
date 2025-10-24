package jp.co.sss.spring_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.spring_test.entity.UserRegistration;
import jp.co.sss.spring_test.form.UserLoginForm;
import jp.co.sss.spring_test.repository.UserRegistrationRepository;
import jp.co.sss.spring_test.util.PasswordsUtil;

@Controller
public class UserLoginController {
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	//ログイン画面表示
	@RequestMapping(path = "/userLogin", method = RequestMethod.GET)
	public String showUserLogin(Model model) {
		model.addAttribute("userLoginForm", new UserLoginForm());
		return "/user/userLogin";
	}
	//ログイン認証処理
	@RequestMapping(path = "/userLogin", method = RequestMethod.POST)
	public String doUserlogin(
		@Validated @ModelAttribute("userLoginForm") UserLoginForm form,
		BindingResult result,
		Model model,
        RedirectAttributes redirectAttributes) {
		//バリテーションエラー(アノテーション)
		if (result.hasErrors()) {
			return "/user/userLogin";
		}
		//メールアドレスでユーザ－検索
		UserRegistration userRegistration = userRegistrationRepository.findByEmail(form.getEmail());
		//ユーザーが存在しない場合はすぐ戻す
		if (userRegistration == null) {
			model.addAttribute("userLoginError", "メールアドレスまたはパスワードが正しくありません。");
			return "/user/userLogin";
		}
		//パスワード認証(PBKDF2)
		boolean isValid = PasswordsUtil.verifyPasswords(form.getPasswords(), userRegistration.getPasswords());
		//パスワードが一致しない場合
		if (!isValid) {
			model.addAttribute("userLoginError", "メールアドレスまたはパスワードが正しくありません。");
			return "/user/userLogin";
		}
		//認証成功
		redirectAttributes.addFlashAttribute("loginSuccess", "ログインに成功しました！");
		redirectAttributes.addFlashAttribute("userName", userRegistration.getUser_name());
		return "redirect:/product/productTop";
	}
}