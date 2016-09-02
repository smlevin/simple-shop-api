package ru.checkout.shop.api.dao.impl;

import com.google.common.collect.Lists;
import ru.checkout.shop.api.dao.ProductDao;
import ru.checkout.shop.api.exception.ProductNotFoundException;
import ru.checkout.shop.api.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Реализация на Hibernate.
 */
public class ProductDaoImpl implements ProductDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Product getProductById(long id) throws ProductNotFoundException {
		Product product = entityManager.find(Product.class, id);
		if (product == null) {
			throw new ProductNotFoundException(id);
		}
		return product;
	}

	@Override
	public List<Product> getProducts(List<Long> ids) throws ProductNotFoundException {
		List<Product> products = Lists.newArrayList();
		//FIXME Думаю, здесь можно оптимальнее вытащить повторяющееся объекты из БД.
		for (Long id : ids) {
			Product product = getProductById(id);
			if (product != null) {
				products.add(product);
			}
		}
		return products;
	}

	@Override
	public void saveProduct(Product product) {
		if (product.getId() == null) {
			entityManager.persist(product);
		} else {
			entityManager.merge(product);
		}
	}
}
