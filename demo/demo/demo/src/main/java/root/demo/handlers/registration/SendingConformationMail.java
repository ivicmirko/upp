package root.demo.handlers.registration;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.SystemUser;
import root.demo.services.SystemUserService;

@Service
public class SendingConformationMail implements JavaDelegate {
	
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    SystemUserService systemUserService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String receiverMail="";
		
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration");
        for(FormSubmissionDto dto: registration){
            if(dto.getFieldId().equals("formEmail")){
                receiverMail = dto.getFieldValue();
            }
        }
        System.out.println("Maiil"+receiverMail);
        SystemUser user = systemUserService.findOneByEmail(receiverMail);
        System.out.println("korisni"+user.getUsername());
		
		try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(receiverMail);
            message.setSubject("Potvrda registracije");

            String confirmationUrl
                    =  "http://localhost:4200/activation/" + user.getId() + "/" + execution.getProcessInstanceId();
            message.setText(user.getName() + " " + user.getSurname() + "dobrodosli u Naucnu centralu\n\n" +
                    "Kako bi zavrsili Vas proces registracije, kliknite na sledeci link\n "
                    + confirmationUrl + "\n\n Vasa NaucnaCentrala");
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

}
