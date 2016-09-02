package ru.checkout.shop.api.dao;

import ru.checkout.shop.api.exception.ProductNotFoundException;
import ru.checkout.shop.api.model.Product;

import java.util.List;

/**
 * Интерфейс работы с сущностью "Продукт"
 */
public interface ProductDao {
	/**
	 * Получить продукт по его идентификатору.
	 * @param id Уникальный идентификатор продукта.
	 * @return продукт.
	 * @throws ProductNotFoundException Продукт не найден.
	 */
	Product getProductById(long id) throws ProductNotFoundException;

	/**
	 * Получить список продуктов по списоку идентификаторов.
	 * @param ids Список идентификаторов продуктов.
	 * @return список продуктов.
	 * @throws ProductNotFoundException Если какой-то из продуктов не существует.
	 */
	List<Product> getProducts(List<Long> ids) throws ProductNotFoundException;

	/**
	 * Сохранить/создать продукт.
	 * @param product продукт для сохранения.
	 */
	void saveProduct(Product product);
}
