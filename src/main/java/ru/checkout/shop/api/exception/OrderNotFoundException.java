package ru.checkout.shop.api.exception;

/**
 * Ошибка должна возникать, если заказ не найден.
 */
public class OrderNotFoundException extends Exception {
	String uid;
	long id;

	public OrderNotFoundException(String uid) {
		this.uid = uid;
	}

	public OrderNotFoundException(long id) {
		this.id = id;
	}
}
