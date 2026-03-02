package ru.kuzin.CornCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Role findByName(String roleName);

}
