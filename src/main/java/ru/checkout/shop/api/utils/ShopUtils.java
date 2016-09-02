package ru.checkout.shop.api.utils;

import ru.checkout.shop.api.model.Product;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Утилитарный класс.
 */
public class ShopUtils {
	/**
	 * Возвращает текущее время в timestamp.
	 * @return текущее время в UNIXTIME.
	 */
	public static int now() {
		return toTimestamp(System.currentTimeMillis());
	}

	/**
	 * Преобразует timestamp в миллисекундах в секунды.
	 * @param time timestamp в миллисекундах.
	 * @return timestamp в секундах.
	 */
	public static int toTimestamp(long time) {
		return (int) TimeUnit.MILLISECONDS.toSeconds(time);
	}

	public static double calcNewUserScore(double score, List<Product> products) {
		for (Product product : products) {
			score -= product.getPrice();
		}
		return score;
	}
}
