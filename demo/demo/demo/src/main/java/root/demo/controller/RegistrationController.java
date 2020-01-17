package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.ScienceArea;
import root.demo.model.SystemUser;
import root.demo.services.ScienceAreaService;
import root.demo.services.SystemUserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	ScienceAreaService scienceAreaService;
	
	@Autowired
	SystemUserService systemUserService;
	
	@GetMapping(path="/getDataInputForm",
			produces="application/json")
	public @ResponseBody FormFieldsDto getDataInputForm() {
		
		ProcessInstance pi=runtimeService.startProcessInstanceByKey("registration");
		
		Task task=taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		TaskFormData taskFormData=formService.getTaskFormData(task.getId());
		
		List<FormField> properties=taskFormData.getFormFields();
		List<ScienceArea> scienceAreas=this.scienceAreaService.findAll();
		for(FormField element : properties) {
			if(element.getId().equals("formScienceArea")) {
				for(ScienceArea sa:scienceAreas) {
				element.getProperties().put(String.valueOf(sa.getId()), sa.getName());
			
				}
			}
		}

		return new FormFieldsDto(task.getId(),pi.getId(),properties);
		
	}
	
	@PostMapping(path="/postDataInputForm/{taskId}",
			produces="application/json")
	public @ResponseBody ResponseEntity<?> postDataInputForm(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId){
		HashMap<String, Object> map = this.mapListToDto(dto);

		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String processInstaceId=task.getProcessInstanceId();
		runtimeService.setVariable(processInstaceId, "registration", dto);
		
		String check="";
		String email="";
		String username="";
		String reviewer="false";
		for(FormSubmissionDto formField:dto) {
			if(formField.getFieldId().equals("formEmail")) {
				email=formField.getFieldValue();
			}
			
			if(formField.getFieldId().equals("formUsername")) {
				username=formField.getFieldValue();
			}
			
			if(formField.getFieldId().equals("formReviewer")) {
				if(formField.getFieldValue().equals("true") || formField.getFieldValue().equals(true)) {
					System.out.println("Zeli biti rew");
					reviewer="true";
				}
			}
		}
		runtimeService.setVariable(processInstaceId, "confirmed", false);
		runtimeService.setVariable(processInstaceId, "username", username);
		runtimeService.setVariable(processInstaceId, "reviewer", reviewer);

		check+=systemUserService.checkCredentials(username, email);
		System.out.println("Check=="+check);

		formService.submitTaskForm(taskId, map);		
//		if(check.equals("")) {
//			return new ResponseEntity<>(HttpStatus.OK);
//		}else {
//			return new ResponseEntity<String>(check,HttpStatus.BAD_REQUEST);
//		}
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

		
		
	}
	
	@GetMapping(path="/activating/{userid}/{processId}",
			produces="application/json")
	public @ResponseBody ResponseEntity<?> activateUser(@PathVariable Long userid,@PathVariable String processId){
		
		SystemUser user=systemUserService.findOneById(userid);
		System.out.println("Pronasao usera"+user.getUsername());
		runtimeService.setVariable(processId, "confirmed", true);
		//user.setActive(true);
		runtimeService.setVariable(processId, "revName", user.getName());
		runtimeService.setVariable(processId, "revSurname", user.getSurname());
		//systemUserService.saveSystemUser(user);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path="/getReviewerRequests",
			produces="application/json")
	public @ResponseBody ResponseEntity<?> getReviewerRequests(){
		System.out.println("Taskovi za admina");
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("adminNC").list();
		System.out.println("AdminNc"+tasks.size());
        List<FormFieldsDto> formFieldsDtos = new ArrayList<>();
        for(Task task: tasks){
            if(task.getName().equals("Confirm As Reviewer")) {
                FormFieldsDto formFieldsDto = new FormFieldsDto();
                formFieldsDto.setTaskId(task.getId());

                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                List<FormField> properties = taskFormData.getFormFields();

                formFieldsDto.setFormFields(properties);
                formFieldsDtos.add(formFieldsDto);
            }
        }
        System.out.println("Ima taskova"+formFieldsDtos.size());
        return new ResponseEntity(formFieldsDtos,  HttpStatus.OK);

	}
	
	@PostMapping(path="/confirmingReviewer/{taskId}",consumes="text/plain",
			produces="application/json")
	public @ResponseBody ResponseEntity<?> confirmingReviewer(@PathVariable String taskId ,@RequestBody String decision){
		
		System.out.println("potvrda recenzentaa"+decision);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

		runtimeService.setVariable(processInstanceId, "decision", decision);

        taskService.complete(taskId);
        
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
}
