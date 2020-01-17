package root.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.SystemUser;
import root.demo.repository.SystemUserRepository;

@Service
public class SystemUserService {

	@Autowired
	SystemUserRepository systemUserRepository;
	
	public SystemUser saveSystemUser(SystemUser systemUser) {
		System.out.println("usao u cuvanje");
		return systemUserRepository.saveAndFlush(systemUser);
	}
	
	public SystemUser findOneById(long id) {
		return systemUserRepository.findOneById(id);
	}
	
	public SystemUser findOneByEmail(String email) {
		return systemUserRepository.findOneByEmail(email);
	}
	
	public SystemUser findOneByUsername(String username) {
		return systemUserRepository.findOneByUsername(username);
	}
	
	public String checkCredentials(String username,String email) {
		String retVal="";
		System.out.println("Provera kredencijala");
		SystemUser user;
		user=systemUserRepository.findOneByEmail(email);
		if(user!=null) {
			retVal+="Greska! Email vec postoji. \n";
		}
		user=null;
		user=systemUserRepository.findOneByUsername(username);
		if(user!=null) {
			retVal+="Username vec postoji";
		}
		
		return retVal;
	}
	
}
