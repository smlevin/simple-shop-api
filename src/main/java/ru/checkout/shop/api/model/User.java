package ru.checkout.shop.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Модель, описывающая сущность "Пользователь".
 */
@Entity
@Table(name = "shop_users")
public class User extends AbstractEntity {
	private double score;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public static class UserBuilder {
		private User user;

		public UserBuilder() {
			user = new User();
		}

		public UserBuilder withId(Long id) {
			user.setId(id);
			return this;
		}

		public UserBuilder withScore(double score) {
			user.score = score;
			return this;
		}

		public User build() {
			return user;
		}
	}
}
