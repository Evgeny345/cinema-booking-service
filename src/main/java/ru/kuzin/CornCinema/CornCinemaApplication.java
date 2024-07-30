package ru.kuzin.CornCinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spring.data.impl.repository.BlazePersistenceRepositoryFactoryBean;

@SpringBootApplication
@EnableEntityViews("ru.kuzin.cinemaBlaze.entityView")
@EnableJpaRepositories(repositoryFactoryBeanClass = BlazePersistenceRepositoryFactoryBean.class)
@EnableCaching
public class CornCinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CornCinemaApplication.class, args);
	}

}
