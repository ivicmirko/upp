package root.demo.services;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import root.demo.model.Magazine;
import root.demo.repository.MagazineRepository;

@Service
public class MagazineService {

	@Autowired
	MagazineRepository magazineRepository;
	
	public Magazine saveMagazine(Magazine magazine) {
		return this.magazineRepository.save(magazine);
	}
}
