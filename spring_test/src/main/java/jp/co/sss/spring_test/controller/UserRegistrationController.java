package jp.co.sss.spring_test.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.spring_test.form.UserRegistrationForm;
import jp.co.sss.spring_test.repository.UserRegistrationRepository;
import jp.co.sss.spring_test.service.UserRegistrationService;

@Controller
public class UserRegistrationController {
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	@Autowired
	private UserRegistrationService userRegistrationService;
	//ユーザー新規登録画面表示
	@RequestMapping(path = "/newRegistration", method = RequestMethod.GET)
	public String showNewRegistration(Model model) {
		//空のフォーム用オブジェクトを用意してmodelに入れる
		model.addAttribute("userRegistrationForm", new UserRegistrationForm());
		//[http://localhost:9991/spring_test/newRegistration]を表示
		return "/user/newRegistration"; }
	//新規登録処理
	@RequestMapping(path = "/newRegistration", method = RequestMethod.POST)
	public String createNewRegistration(@Valid @ModelAttribute UserRegistrationForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		//バリテーションエラー(アノテーション)
		if (result.hasErrors()) {
			return "/user/newRegistration";
		}
		//パスワードとパスワード(確認用)が一致しているか確認
		if (!form.getPasswords().equals(form.getPasswordsConfirm())) {
			model.addAttribute("passwordsError", "パスワードが一致しません。");
			return "/user/userRegistration";
		}
		//メールアドレスが既にDBに登録されていないか確認
		if (userRegistrationRepository.findByEmail(form.getEmail()) != null) {
			redirectAttributes.addFlashAttribute("userLoginError", "このメールアドレスは既に登録されています。ログインして下さい。");
			return "redirect:/userLogin";
		}
		//ユーザー登録処理
		userRegistrationService.registerUser(form);
		//完了画面へ
		redirectAttributes.addFlashAttribute("registrationSuccess", "新規登録が完了しました。");
		return "/user/userLogin";
	}
}