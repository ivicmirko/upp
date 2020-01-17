package root.demo.handlers.registration;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.SystemUser;
import root.demo.services.SystemUserService;

@Service
public class ConfirmingReviewer implements JavaDelegate {

	@Autowired
	SystemUserService systemUserService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Usao potvrda recenzenta");
		 String username = (String) execution.getVariable("username");
		 String decision=(String) execution.getVariable("decision");
	        System.out.println("username: " + username);

	        try{
	        	SystemUser user=systemUserService.findOneByUsername(username);
	            user.setReviewer(decision);
	            user=systemUserService.saveSystemUser(user);	            
	        }catch(NullPointerException e){
	            System.out.println("Nema korisnika");
	        }
	
	}

}
