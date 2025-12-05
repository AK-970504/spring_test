package jp.co.sss.spring_test.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationForm {
	@NotBlank(message = "{NotBlank.UserRegistrationForm.user_name}")
	private String user_name;
	@NotBlank(message = "{NotBlank.UserRegistrationForm.email}")
	@Email
	private String email;
	@NotBlank(message = "{NotBlank.UserRegistrationForm.passwords}")
	@Size (min = 8, max = 20, message = "{Size.UserRegistrationForm.passwords}")
	@Pattern (regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$", message = "{Pattern.UserRegistrationForm.passwords}")
	private String passwords;
	@NotBlank(message = "{NotBlank.UserRegistrationForm.passwordsConfirm}")
	private String passwordsConfirm;
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
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
	public String getPasswordsConfirm() {
		return passwordsConfirm;
	}
	public void setPasswordsConfirm(String passwordsConfirm) {
		this.passwordsConfirm = passwordsConfirm;
	}
}