package jp.co.sss.spring_test.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer user_id;
	@Column (nullable = false)
	private String user_name = "";
	@Column (nullable = false) 
	private String user_name_kana = "";
	@Column (nullable = false)
	private String email;
	@Column (nullable = false)
	private String phone = "";
	@Column (nullable = false)
	private String user_address = "";
	@Column (nullable = false)
	private String passwords;
	@Column (nullable = false)
	private LocalDateTime created_at;
	@Column (nullable = false)
	private LocalDateTime updated_at;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_name_kana() {
		return user_name_kana;
	}
	public void setUser_name_kana(String user_name_kana) {
		this.user_name_kana = user_name_kana;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
}