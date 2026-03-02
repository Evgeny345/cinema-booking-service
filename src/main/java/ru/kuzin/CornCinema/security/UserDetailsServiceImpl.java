package ru.kuzin.CornCinema.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kuzin.CornCinema.dao.UserRepository;
import ru.kuzin.CornCinema.models.Role;
import ru.kuzin.CornCinema.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if (user == null)
			throw new UsernameNotFoundException("User with name " + username + " not found");
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRoleToAuthority(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRoleToAuthority(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
	}

}
