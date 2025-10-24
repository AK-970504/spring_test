package jp.co.sss.spring_test.bean;

import java.io.Serializable;

public class UserRegistrationBean implements Serializable {
	private Integer user_id;
	private String email;
	private String passwords;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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
}