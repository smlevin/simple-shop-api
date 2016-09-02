package ru.checkout.shop.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Модель, описывающая сущность "Заказ".
 */
@Entity
@Table(name = "shop_orders")
public class Order extends AbstractEntity {
	/**
	 * Максимальная длина для артикула.
	 */
	private static final int MAX_UID_LENGTH = 40;

	/**
	 * Уникальный ключ заказа. В ТЗ был - не знаю зачем он...
	 */
	@Column(length = MAX_UID_LENGTH, unique = true)
	private String uid;

	/**
	 * Дата заказа.
	 */
	private int timestamp;

	/**
	 * Статус заказа.
	 */
	private OrderStatus status;

	/**
	 * Список продуктов.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "shop_order_products")
	private List<Product> productsList;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<Product> productsList) {
		this.productsList = productsList;
	}

	public static class OrderBuilder {
		private Order order;

		public OrderBuilder() {
			order = new Order();
		}

		public OrderBuilder withId(Long id) {
			order.setId(id);
			return this;
		}

		public OrderBuilder withUid(String uid) {
			order.uid = uid;
			return this;
		}

		public OrderBuilder withTimestamp(int timestamp) {
			order.timestamp = timestamp;
			return this;
		}

		public OrderBuilder withStatus(OrderStatus status) {
			order.status = status;
			return this;
		}

		public OrderBuilder withProductsList(List<Product> productsList) {
			order.productsList = productsList;
			return this;
		}

		public Order build() {
			return order;
		}
	}
}
