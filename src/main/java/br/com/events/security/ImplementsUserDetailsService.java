package br.com.events.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.events.model.UserAccount;
import br.com.events.repository.UserAccountRepository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {

	@Autowired
	UserAccountRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount u = ur.findByUsername(username);
		
		if (u == null)
			throw new UsernameNotFoundException("Usuário não encontrado");
		else return u;
		
	}

}
