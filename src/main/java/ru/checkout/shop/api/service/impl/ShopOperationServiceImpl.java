package ru.checkout.shop.api.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.checkout.shop.api.dao.OrderDao;
import ru.checkout.shop.api.dao.ProductDao;
import ru.checkout.shop.api.exception.OrderNotFoundException;
import ru.checkout.shop.api.model.Order;
import ru.checkout.shop.api.model.OrderStatus;
import ru.checkout.shop.api.model.Product;
import ru.checkout.shop.api.service.ShopOperationService;
import ru.checkout.shop.api.utils.ShopUtils;

import java.util.List;
import java.util.UUID;

public class ShopOperationServiceImpl implements ShopOperationService {
	private static final Log log = LogFactory.getLog(ShopOperationServiceImpl.class);

	@Autowired
	private ProductDao productDao;

	@Autowired
	private OrderDao orderDao;

	@Override
	public Order createOrder(long userId, List<Long> productsList) throws Exception {
		List<Product> products = productDao.getProducts(productsList);
		Order order = new Order.OrderBuilder()
				.withProductsList(products)
				.withStatus(OrderStatus.NEW)
				.withTimestamp(ShopUtils.now())
				.withUid(UUID.randomUUID().toString())
				.build();
		createOrderSafety(userId, order);
		return order;
	}

	@Override
	public Order getOrderByUid(String orderUid) throws OrderNotFoundException {
		return orderDao.getOrderByUid(orderUid);
	}

	/**
	 * Обработчик для корректного сохранения заказа в многопоточной среде.
	 * @param userId Уникальный идентификатор пользователя.
	 * @param order Заказ, который надо сохранить.
	 * @throws Exception Что-то пошло не так.
	 */
	private void createOrderSafety(long userId, Order order) throws Exception {
		int attempt = 0;
		while (true) {
			try {
				orderDao.createOrder(userId, order);
				return;
			} catch (RuntimeException e) { // Тест для обработки MySQLTransactionRollbackException
				attempt++;
				log.debug("Deadlock catched (" + attempt + ")", e);
			}
		}
	}
}
