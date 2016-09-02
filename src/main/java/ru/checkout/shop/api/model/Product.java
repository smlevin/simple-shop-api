package ru.checkout.shop.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Модель, описывающая сущность "Продукт".
 */
@Entity
@Table(name = "shop_products")
public class Product extends AbstractEntity {
	/**
	 * Максимальная длина для артикула.
	 */
	private static final int MAX_VENDOR_CODE_LENGTH = 30;
	/**
	 * Максимальная длина для наименования.
	 */
	private static final int MAX_NAME_LENGTH = 250;

	/**
	 * Актикул.
	 */
	@Column(length = MAX_VENDOR_CODE_LENGTH)
	private String vendorCode;

	/**
	 * Наименование.
	 */
	@Column(length = MAX_NAME_LENGTH)
	private String name;

	/**
	 * Стоимость.
	 */
	private double price;

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static class ProductBuilder {
		private Product product;

		public ProductBuilder() {
			product = new Product();
		}

		public ProductBuilder withId(Long id) {
			product.setId(id);
			return this;
		}

		public ProductBuilder withVendorCode(String vendorCode) {
			product.vendorCode = vendorCode;
			return this;
		}

		public ProductBuilder withName(String name) {
			product.name = name;
			return this;
		}

		public ProductBuilder withPrice(double price) {
			product.price = price;
			return this;
		}

		public Product build() {
			return product;
		}
	}
}
