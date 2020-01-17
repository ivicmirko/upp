package root.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.SystemUser;


public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

	List<SystemUser> findAll();
	SystemUser findOneByUsername(String username);
	SystemUser findOneById(Long id);
	SystemUser findOneByEmail(String email);
}
