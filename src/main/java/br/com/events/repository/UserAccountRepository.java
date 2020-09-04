package br.com.events.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.events.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

	UserAccount findByUsername(String username);
}
