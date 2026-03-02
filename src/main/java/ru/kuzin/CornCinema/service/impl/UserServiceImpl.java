package ru.kuzin.CornCinema.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.RoleRepository;
import ru.kuzin.CornCinema.dao.UserRepository;
import ru.kuzin.CornCinema.entityView.userView.UserCreateView;
import ru.kuzin.CornCinema.entityView.userView.UserFormView;
import ru.kuzin.CornCinema.entityView.userView.UserFormViewImpl;
import ru.kuzin.CornCinema.models.User;
import ru.kuzin.CornCinema.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private EntityManager entityManager;
	private EntityViewManager entityViewManager;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}
	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {this.roleRepository = roleRepository;}
	@Autowired
	public void setEntityManager(EntityManager entityManager) {this.entityManager = entityManager;}
	@Autowired
	public void setEntityViewManager(EntityViewManager entityViewManager) {this.entityViewManager = entityViewManager;}
	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {this.passwordEncoder = passwordEncoder;}
	
	@Override
	public UserFormView getUserForm() {
		UserFormView user = entityViewManager.create(UserFormView.class);
		return user;
	}
	
	@Override
	@Transactional
	public void createUser(UserFormViewImpl userForm, String roleName) {
		UserCreateView user = entityViewManager.convert(userForm, UserCreateView.class, ConvertOption.CREATE_NEW);
		/*entityViewManager.saveWith(entityManager, user)
						 .onPrePersist(UserCreateView.class, User.class, new PrePersistEntityListener<UserCreateView, User>() {
							 
							 @Override
							 public void prePersist(EntityViewManager evm, EntityManager em, UserCreateView view, User entity) {
								 LocalDateTime createDate = LocalDateTime.now();
								 entity.setRegistrationDate(createDate);
							 }
						 })
						 .flush();*/
		user.setRegistrationDate(LocalDateTime.now());
		String encodedPassword = passwordEncoder.encode(userForm.getPassword());
		user.setPassword(encodedPassword);
		user.getRoles().add(roleRepository.findByName(roleName));
		entityViewManager.save(entityManager, user);
	}
	
	@Override
	public User getUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	@Override
	public long amountOfUsers() {
		return userRepository.count();
	}
	
}
