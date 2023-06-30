package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.models.User;

public interface UserService {


	User insert(User user);

	User update(User user);

	void delete(Integer id);

	User findById(Integer id);

	List<User> listAll();

	List<User> findByNameStarting(String name);

}
