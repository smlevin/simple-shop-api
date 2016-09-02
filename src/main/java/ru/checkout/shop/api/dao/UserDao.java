package ru.checkout.shop.api.dao;


import ru.checkout.shop.api.exception.UserNotFoundException;
import ru.checkout.shop.api.model.User;

/**
 * Интерфейс работы с сущностью "Пользователь"
 */
public interface UserDao {
	/**
	 * Получить пользователя по его уникальному идентификатору.
	 * @param id Уникальный идентификатор пользователя.
	 * @return пользователя.
	 * @throws UserNotFoundException Пользователь не найден.
	 */
	User getUserById(long id) throws UserNotFoundException;

	/**
	 * Сохранить/создать пользователя.
	 * @param user пользователь.
	 */
	void saveUser(User user);
}
