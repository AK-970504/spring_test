package jp.co.sss.spring_test.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserLoginForm {
	@NotBlank(message = "{NotBlank.UserLoginForm.email}")
	@Email(message = "{Email.UserLoginForm.email}")
	private String email;
	@NotBlank(message = "{NotBlank.UserLoginForm.passwords}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{Pattern.UserLoginForm.passwords}")
	private String passwords;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
}