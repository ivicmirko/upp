package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import com.sun.javafx.geom.transform.Identity;

import root.demo.model.FormSubmissionDto;
import root.demo.model.ScienceArea;
import root.demo.model.SystemUser;

@Service
public class RegistrationValidationService implements JavaDelegate {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	ScienceAreaService scienceAreaService;
	
	@Autowired
	JavaMailSender emailSender;
	
	@Autowired
	SystemUserService systemUserService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("TASK222222222222222222222");
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");

        SystemUser user=new SystemUser();
        String receiverMail = "";

        for(int i=0; i<registration.size(); i++){
            if(registration.get(i).getFieldId().equals("formName")){
                user.setName(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formSurname")){
                user.setSurname(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formTitle")){
                user.setTitle(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formCity")){
                user.setCity(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formCountry")){
                user.setCountry(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formEmail")){
                receiverMail = registration.get(i).getFieldValue();
                user.setEmail(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formUsername")){
                user.setUsername(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formPassword")){
            	user.setPassword(registration.get(i).getFieldValue());
            }else if(registration.get(i).getFieldId().equals("formReviewer")){
                
                if(registration.get(i).getFieldValue().equals(false) || registration.get(i).getFieldValue().equals("false") || registration.get(i).getFieldValue().equals("")){
                    user.setReviewer("no");
                }else if(registration.get(i).getFieldValue().equals(true) || registration.get(i).getFieldValue().equals("true")){
                    user.setReviewer("asking");
                }
            }else if(registration.get(i).getFieldId().equals("formScienceArea1")){
            	System.out.println("ovdee");
            	String allAreas=registration.get(i).getFieldValue();
            	String [] areas=allAreas.split(",");
            	ScienceArea scienceArea;
            	System.out.println(allAreas);
            	for(int ii=0; ii< areas.length; ii++ ) {
            		System.out.println(areas[ii]);
            		scienceArea=scienceAreaService.findOneById(Long.parseLong(areas[ii]));
            		System.out.println("oblast"+scienceArea.getName());
            		if(scienceArea != null) {
            			user.getScienceAreas().add(scienceArea);
            		}
            	}
            }
        }
        System.out.println("AAAaaaaa"+user.getScienceAreas().get(0).getName());
        execution.setVariable("registration", registration);

        user.setActive(false);
        System.out.println("cuvanje usera"+user.getPassword());
        user = systemUserService.saveSystemUser(user);
        
        
	}
}
