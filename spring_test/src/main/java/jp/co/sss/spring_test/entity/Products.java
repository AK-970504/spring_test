package jp.co.sss.spring_test.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (nullable = false)
	private Integer product_id;
	@Column (nullable = false)
	private String product_name;
	@Column (nullable = false)
	private Integer price;
	@Column (nullable = false)
	private Integer tax_price;
	@Column (nullable = false)
	private Integer stock;
	@Column (nullable = false)
	private String comment;
	@Column (nullable = false)
	private String img_path;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Companies company;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Categories category;
	@Column (nullable = false)
	private Integer include_tax;
	@Column (nullable = false)
	private LocalDateTime created_at;
	@Column (nullable = false)
	private LocalDateTime updated_at;
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getTax_price() {
		return tax_price;
	}
	public void setTax_price(Integer tax_price) {
		this.tax_price = tax_price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public Companies getCompany() {
		return company;
	}
	public void setCompany(Companies company) {
		this.company = company;
	}
	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	public Integer getInclude_tax() {
		return include_tax;
	}
	public void setInclude_tax(Integer include_tax) {
		this.include_tax = include_tax;
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