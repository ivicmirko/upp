package root.demo.handlers.registration;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import root.demo.model.SystemUser;
import root.demo.services.SystemUserService;


@Service
public class ConformationUser implements JavaDelegate {

	@Autowired
    JavaMailSender emailSender;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    SystemUserService systemUserService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Usao u aktivaciju korinsik");
		System.out.println("Kako je"+execution.getVariable("reviewer"));

		String username = (String) execution.getVariable("username");

        SystemUser user  = systemUserService.findOneByUsername(username);
        if(user == null || user.isActive() == true) {
            System.out.println("Nema ovog korisnika, ili je vec aktiviran");
        }else{
            user.setActive(true);
            systemUserService.saveSystemUser(user);

        }
       System.out.println("Izasao iz potvrde korisnikaa"); 
	}
}
