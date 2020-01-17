package root.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ScienceArea;
import root.demo.repository.ScienceAreaRepository;

@Service
public class ScienceAreaService {

	@Autowired
	ScienceAreaRepository scienceAreaRepository;
	
	public List<ScienceArea> findAll(){
		return scienceAreaRepository.findAll();
	}
	
	public ScienceArea findOneById(long id) {
		return scienceAreaRepository.findOneById(id);
	}
}
