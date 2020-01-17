package root.demo.handlers.creatingnewmagazine;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import root.demo.model.FormSubmissionDto;
import root.demo.model.Magazine;
import root.demo.model.ScienceArea;
import root.demo.model.SystemUser;
import root.demo.services.MagazineService;
import root.demo.services.ScienceAreaService;
import root.demo.services.SystemUserService;

public class SavingMagazine implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	ScienceAreaService scienceAreaService;
	
	@Autowired
	JavaMailSender emailSender;
	
	@Autowired
	SystemUserService systemUserService;
	
	@Autowired
	MagazineService magazineService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		List<FormSubmissionDto> newMagazineDTO = (List<FormSubmissionDto>) execution.getVariable("creatingMagazine");

        Magazine magazine=new Magazine();

        for(int i=0; i<newMagazineDTO.size(); i++){
            if(newMagazineDTO.get(i).getFieldId().equals("formMagazineName")){
                magazine.setName(newMagazineDTO.get(i).getFieldValue());
            }else if(newMagazineDTO.get(i).getFieldId().equals("formISSNNumber")){
            	magazine.setIssn(newMagazineDTO.get(i).getFieldValue());
            }else if(newMagazineDTO.get(i).getFieldId().equals("paymentModeForm")){
                
                if(newMagazineDTO.get(i).getFieldValue().equals("autorima")){
                	magazine.setOpenAccess(true);
                }else if(newMagazineDTO.get(i).getFieldValue().equals(true) || newMagazineDTO.get(i).getFieldValue().equals("true")){
                    magazine.setOpenAccess(false);
                }
            }else if(newMagazineDTO.get(i).getFieldId().equals("formScienceArea1")){
            	System.out.println("ovdee");
            	String allAreas=newMagazineDTO.get(i).getFieldValue();
            	String [] areas=allAreas.split(",");
            	ScienceArea scienceArea;
            	System.out.println(allAreas);
            	for(int ii=0; ii< areas.length; ii++ ) {
            		System.out.println(areas[ii]);
            		scienceArea=scienceAreaService.findOneById(Long.parseLong(areas[ii]));
            		System.out.println("oblast"+scienceArea.getName());
            		if(scienceArea != null) {
            			magazine.getScienceAreas().add(scienceArea);
            		}
            	}
            }
        }
        execution.setVariable("magazineInformation", magazine);

        magazine.setActive(false);
        magazine = magazineService.saveMagazine(magazine);
	}

}
