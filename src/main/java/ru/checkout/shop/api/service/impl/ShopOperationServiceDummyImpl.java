package ru.checkout.shop.api.service.impl;

import ru.checkout.shop.api.model.Order;
import ru.checkout.shop.api.model.OrderStatus;
import ru.checkout.shop.api.model.Product;
import ru.checkout.shop.api.service.ShopOperationService;
import ru.checkout.shop.api.utils.ShopUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Пустая реализация.
 */
public class ShopOperationServiceDummyImpl implements ShopOperationService {
	private static final long UNSET = 0L;
	private static final String DUMMY = "dummy";

	@Override
	public Order createOrder(long userId, List<Long> productsList) {
		return new Order.OrderBuilder()
				.withId(UNSET)
				.withProductsList(new ArrayList<Product>())
				.withUid(UUID.randomUUID().toString())
				.withStatus(OrderStatus.NEW)
				.withTimestamp(ShopUtils.now())
				.build();
	}

	@Override
	public Order getOrderByUid(String orderUid) {
		return new Order.OrderBuilder()
				.withId(UNSET)
				.withProductsList(new ArrayList<Product>())
				.withUid(DUMMY)
				.withStatus(OrderStatus.DONE)
				.withTimestamp(ShopUtils.now())
				.build();
	}
}
