package ru.checkout.shop.api.exception;

/**
 * Ошибка должна возникать, если продукт не найден.
 */
public class ProductNotFoundException extends Exception {
	long id;

	public ProductNotFoundException(long id) {
		this.id = id;
	}
}
