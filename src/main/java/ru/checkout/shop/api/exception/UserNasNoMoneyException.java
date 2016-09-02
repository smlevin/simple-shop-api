package ru.checkout.shop.api.exception;

/**
 * Ошибка должна возникать, если у пользователя больше нет денег.
 */
public class UserNasNoMoneyException extends Exception {
	long userId;

	public UserNasNoMoneyException(long userId) {
		this.userId = userId;
	}
}
