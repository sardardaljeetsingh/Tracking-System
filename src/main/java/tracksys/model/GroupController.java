package tracksys.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */
@Controller
@RequestMapping("/group")
public class GroupController {
	@RequestMapping(value ="/create",method = RequestMethod.PUT)
	public ResponseEntity<Group>  create(@RequestBody Group group) {
		try {
			groupRepository.save(group);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Group>(group, HttpStatus.OK);
	}
	  
	  @RequestMapping(value ="/find-by-company/{companyId}",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<Group>> findbyCompany(@PathVariable("id") int companyId) {
		  Iterable<Group> grpLableList = null;
	    try {
	    	Company company = new Company(companyId) ;
	    	grpLableList = groupRepository.findAllByCompany(company);
	    }
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<Group>>(grpLableList, HttpStatus.OK);
	}
	  
	  @RequestMapping(value ="/find-by-parent/{parentGrpId}",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<Group>> findAllByParent(@PathVariable("id") int parentGrpId) {
		  Iterable<Group> grpLableList = null;
	    try {
	    	grpLableList = groupRepository.findAllByParent(parentGrpId);
	    }
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<Group>>(grpLableList, HttpStatus.OK);
	}  
	
	  @Autowired
	  private GroupRepository groupRepository;
}
