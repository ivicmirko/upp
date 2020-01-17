package root.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.ScienceArea;

public interface ScienceAreaRepository extends JpaRepository<ScienceArea, Long> {

	List<ScienceArea> findAll();
	ScienceArea findOneById(Long id);
}
