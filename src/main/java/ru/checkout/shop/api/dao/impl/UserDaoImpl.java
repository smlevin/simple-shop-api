package ru.checkout.shop.api.dao.impl;

import ru.checkout.shop.api.dao.UserDao;
import ru.checkout.shop.api.exception.UserNotFoundException;
import ru.checkout.shop.api.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Реализация на Hibernate.
 */
public class UserDaoImpl implements UserDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveUser(User user) {
		if (user.getId() == null) {
			entityManager.persist(user);
		} else {
			entityManager.merge(user);
		}
	}

	@Override
	public User getUserById(long id) throws UserNotFoundException {
		User user = entityManager.find(User.class, id);
		if (user == null) {
			throw new UserNotFoundException(id);
		}
		return user;
	}
}
