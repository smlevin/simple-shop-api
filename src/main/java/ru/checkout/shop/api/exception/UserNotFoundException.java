package ru.checkout.shop.api.exception;

/**
 * Ошибка должна возникать, если пользователь не найден.
 */
public class UserNotFoundException extends Exception {
	long userId;

	public UserNotFoundException(long userId) {
		this.userId = userId;
	}
}
