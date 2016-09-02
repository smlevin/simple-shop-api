package ru.checkout.shop.api.service;

import ru.checkout.shop.api.exception.OrderNotFoundException;
import ru.checkout.shop.api.exception.ProductNotFoundException;
import ru.checkout.shop.api.exception.UserNasNoMoneyException;
import ru.checkout.shop.api.exception.UserNotFoundException;
import ru.checkout.shop.api.model.Order;

import java.util.List;

/**
 * Интерфейс операций в магазине.
 */
public interface ShopOperationService {
	Order createOrder(long userId, List<Long> productsList) throws Exception;

	Order getOrderByUid(String orderUid) throws OrderNotFoundException;
}
