package jp.co.sss.spring_test.dto;

import jp.co.sss.spring_test.entity.Products;

public class ProductDto {
	private Integer product_id;
	private String product_name;
	private Integer price;
	private Integer tax_price;
	private String company_name;
	public ProductDto(Products p) {
		this.product_id = p.getProduct_id();
		this.product_name = p.getProduct_name();
		this.price = p.getPrice();
		this.tax_price = p.getPrice() * 110 / 100;
		if (p.getCompany() != null) {
			this.company_name = p.getCompany().getCompany_name();
		}
	}
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
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
}