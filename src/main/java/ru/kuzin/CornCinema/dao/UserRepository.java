package ru.kuzin.CornCinema.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazebit.persistence.spring.data.annotation.OptionalParam;

import ru.kuzin.CornCinema.entityView.userView.UserView;
import ru.kuzin.CornCinema.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUserName(String userName);
	
	UserView getById(Integer id, @OptionalParam("startDate") LocalDateTime startDate, 
			 					 @OptionalParam("endDate") LocalDateTime endDate);
	
}
