package br.com.events.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementsUserDetailsService userDetails;
	
	// Neste método definimos como as páginas serão liberadas
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().permitAll()			
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	// Neste método definimos os usuários (InMemory) que podem se autenticar
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("admin").password("{noop}admin").roles("ADMIN");
		
		auth.userDetailsService(userDetails)
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Aqui nós simplesente mencionamos as pastas que o Spring Serurity vai ignorar, não preicsa de autenticação
		web.ignoring().antMatchers("/materialize/**", "/css/**");
	}

}