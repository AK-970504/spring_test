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
@Table(name = "sales_items")
public class SalesItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "sale_item_id", nullable = false)
	private Integer sale_item_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Products product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Companies company;
	@Lob
	@Column (nullable = false)
	private String sale_name;
	@Lob
	@Column (nullable = false)
	private String description;
	@Column (nullable = false)
	private Integer discount_rate;
	@Column (nullable = false)
	private String sales_img_path;
	@Column (name = "start_month", nullable = false)
	private LocalDateTime start_month;
	@Column (name = "end_month", nullable = false)
	private LocalDateTime end_month;
	@Column (name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	@Column (name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	public Integer getSale_item_id() {
		return sale_item_id;
	}
	public void setSale_item_id(Integer sale_item_id) {
		this.sale_item_id = sale_item_id;
	}
	public Products getProduct() {
		return product;
	}
	public void setProduct(Products product) {
		this.product = product;
	}
	public Companies getCompany() {
		return company;
	}
	public void setCompany(Companies company) {
		this.company = company;
	}
	public String getSale_name() {
		return sale_name;
	}
	public void setSale_name(String sale_name) {
		this.sale_name = sale_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDiscount_rate() {
		return discount_rate;
	}
	public void setDiscount_rate(Integer discount_rate) {
		this.discount_rate = discount_rate;
	}
	public String getSales_img_path() {
		return sales_img_path;
	}
	public void setSales_img_path(String sales_img_path) {
		this.sales_img_path = sales_img_path;
	}
	public LocalDateTime getStart_month() {
		return start_month;
	}
	public void setStart_month(LocalDateTime start_month) {
		this.start_month = start_month;
	}
	public LocalDateTime getEnd_month() {
		return end_month;
	}
	public void setEnd_month(LocalDateTime end_month) {
		this.end_month = end_month;
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