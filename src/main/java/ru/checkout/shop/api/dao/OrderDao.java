package ru.checkout.shop.api.dao;

import ru.checkout.shop.api.exception.OrderNotFoundException;
import ru.checkout.shop.api.model.Order;

/**
 * Интерфейс работы с сущностью "Заказ"
 */
public interface OrderDao {
	/**
	 * Получить заказ по уникальному идентификатору.
	 * @param id Уникальный идентификатор заказа.
	 * @return заказ.
	 * @throws OrderNotFoundException заказ не найден.
	 */
	Order getOrderById(long id) throws OrderNotFoundException;

	/**
	 * Получить заказ по UID.
	 * @param uid UID.
	 * @return заказ.
	 * @throws OrderNotFoundException заказ не найден.
	 */
	Order getOrderByUid(String uid) throws OrderNotFoundException;

	/**
	 * Сохранить/создать заказ.
	 * @param order Заказ.
	 */
	void saveOrder(Order order);

	/**
	 * Создать новый заказ.
	 * @param userId Уникальный идентификатор пользователя.
	 * @param order Заказ.
	 * @throws Exception Если что-то пошло не так.
	 */
	void createOrder(long userId, Order order) throws Exception;
}
