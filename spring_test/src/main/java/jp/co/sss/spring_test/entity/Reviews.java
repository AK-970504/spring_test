package jp.co.sss.spring_test.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Reviews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (nullable = false)
	private Integer Review_id;;
	//[user_id]を外部キーとして[users]に紐づける
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private Users users;
	//[product_id]を外部キーとして[products]に紐づける
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Products products;
	@Column (nullable = false)
	private Integer rating;
	@Column (nullable = false)
	private String comment;
	@Column (nullable = false)
	private String dummy_user_name;
	@Lob
	@Column (nullable = false)
	private byte[] review_img_path;
	@Column (name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	@Column (name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	public Integer getReview_id() {
		return Review_id;
	}
	public void setReview_id(Integer review_id) {
		Review_id = review_id;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDummy_user_name() {
		return dummy_user_name;
	}
	public void setDummy_user_name(String dummy_user_name) {
		this.dummy_user_name = dummy_user_name;
	}
	public byte[] getReview_img_path() {
		return review_img_path;
	}
	public void setReview_img_path(byte[] review_img_path) {
		this.review_img_path = review_img_path;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}