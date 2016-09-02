package ru.checkout.shop.api.model;

/**
 * Статус заказа.
 */
public enum OrderStatus {
	NEW(0, "Новый"),
	IN_PROGRESS(1, "В отгрузке"),
	DONE(2, "Вручен");

	private int code;
	private String description;

	OrderStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int code() {
		return code;
	}
}
