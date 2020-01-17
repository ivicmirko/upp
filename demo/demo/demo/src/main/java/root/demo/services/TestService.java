package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import root.demo.model.FormSubmissionDto;

@Service
public class TestService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 String var = "Pera";
	      var = var.toUpperCase();
	      execution.setVariable("input", var);
	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	      System.out.println(registration);
	      System.out.println("upisaivaaa");
	      User user = identityService.newUser("");
	      for (FormSubmissionDto formField : registration) {
	    	  System.out.println(formField.getFieldId());
	    	  if(formField.getFieldId().equals("username")) {
				user.setId(formField.getFieldValue());
				System.out.println("Aaaaaaa"+formField.getFieldValue());
			}
			if(formField.getFieldId().equals("password")) {
				user.setPassword(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("enumerationField")) {
				System.out.println(formField.getFieldValue());
				System.out.println(formField.getFieldId()+"ajdee");

			}
			
		}
	      identityService.saveUser(user);
	}
}
