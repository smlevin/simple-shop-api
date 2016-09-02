package ru.checkout.shop.api.dao.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.checkout.shop.api.dao.OrderDao;
import ru.checkout.shop.api.exception.OrderNotFoundException;
import ru.checkout.shop.api.exception.UserNasNoMoneyException;
import ru.checkout.shop.api.exception.UserNotFoundException;
import ru.checkout.shop.api.model.Order;
import ru.checkout.shop.api.model.Order_;
import ru.checkout.shop.api.model.User;
import ru.checkout.shop.api.utils.ShopUtils;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Реализация на Hibernate.
 */
public class OrderDaoImpl implements OrderDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Order getOrderById(long id) throws OrderNotFoundException {
		Order order = entityManager.find(Order.class, id);
		if (order == null) {
			throw new OrderNotFoundException(id);
		}
		return order;
	}

	@Override
	public Order getOrderByUid(String uid) throws OrderNotFoundException {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Order> query = cb.createQuery(Order.class);
		final Root<Order> root = query.from(Order.class);
		query.where(cb.equal(root.get(Order_.uid), uid));
		query.select(root);
		List<Order> result = entityManager.createQuery(query).getResultList();
		if (result.size() == 0) {
			throw new OrderNotFoundException(uid);
		}
		return result.get(0);
	}

	@Override
	public void saveOrder(Order order) {
		if (order.getId() == null) {
			entityManager.persist(order);
		} else {
			entityManager.merge(order);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void createOrder(long userId, Order order) throws Exception {
		User user = entityManager.find(User.class, userId, LockModeType.PESSIMISTIC_READ);
		if (user == null) {
			throw new UserNotFoundException(userId);
		}
		double newScore = ShopUtils.calcNewUserScore(user.getScore(), order.getProductsList());
		if (newScore < 0) {
			throw new UserNasNoMoneyException(userId);
		}
		user.setScore(newScore);
		entityManager.merge(user);
		saveOrder(order);
	}
}
