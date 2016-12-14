package fr.efrei.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import fr.efrei.backend.domain.User;

@Repository
public class UserRepository {
	
//	@PersistenceContext(unitName = "manager1")
	private EntityManager entityManager;
	
	public User save(User user) {
		entityManager.persist(user);
		return user;
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
