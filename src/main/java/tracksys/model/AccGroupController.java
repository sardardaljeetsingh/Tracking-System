package tracksys.model;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/accgroup")
public class AccGroupController {
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public ResponseEntity<AccGroup>  create(@RequestBody AccGroup group) {
		try {
			groupRepository.save(group);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			group.setName(ex.getMessage());
			return new ResponseEntity<>(group ,  HttpStatus.OK);
		}
		return new ResponseEntity<AccGroup>(group, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/delete/{grpid}",method = RequestMethod.DELETE)
	@Transactional
	public @ResponseBody boolean  delete(@PathVariable("grpid") int grpid) {
		boolean accountDelete = false;
		try{
			groupRepository.delete(grpid);
			accountDelete = true;
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return accountDelete;
	}		
	  
	  @RequestMapping(value ="/find-by-company/{companyId}",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<AccGroup>> findbyCompany(@PathVariable("companyId") int companyId) {
		  Iterable<AccGroup> grpLableList = null;
	    try {
	    	Company company = new Company(companyId) ;
	    	grpLableList = groupRepository.findAllByCompany(company);
	    }
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<AccGroup>>(grpLableList, HttpStatus.OK);
	}
	  
	  @RequestMapping(value ="/find-by-parent/{parentGrpId}",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<AccGroup>> findAllByParent(@PathVariable("parentGrpId") int parentGrpId) {
		  Iterable<AccGroup> grpLableList = null;
	    try {
	    	grpLableList = groupRepository.findAllByParent(parentGrpId);
	    }
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<AccGroup>>(grpLableList, HttpStatus.OK);
	}  
	
	
	
	  @Autowired
	  private AccGroupRepository groupRepository;
}
