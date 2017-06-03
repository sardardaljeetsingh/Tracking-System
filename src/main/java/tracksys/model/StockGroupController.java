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
@RequestMapping("/stockgroup")
public class StockGroupController {
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public ResponseEntity<StockGroup>  create(@RequestBody StockGroup group) {
		try {
			groupRepository.save(group);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			group.setName(ex.getMessage());
			return new ResponseEntity<>(group ,  HttpStatus.OK);
		}
		return new ResponseEntity<StockGroup>(group, HttpStatus.OK);
	}
	  
	  @RequestMapping(value ="/find-by-company/{companyId}",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<StockGroup>> findbyCompany(@PathVariable("companyId") int companyId) {
		  Iterable<StockGroup> grpLableList = null;
	    try {
	    	Company company = new Company(companyId) ;
	    	grpLableList = groupRepository.findAllByCompany(company);
	    }
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<StockGroup>>(grpLableList, HttpStatus.OK);
	}
	  
	  @RequestMapping(value ="/find-by-parent/{parentGrpId}",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<StockGroup>> findAllByParent(@PathVariable("parentGrpId") int parentGrpId) {
		  Iterable<StockGroup> grpLableList = null;
	    try {
	    	grpLableList = groupRepository.findAllByParent(parentGrpId);
	    }
		catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Iterable<StockGroup>>(grpLableList, HttpStatus.OK);
	}  
	
	  @Autowired
	  private StockGroupRepository groupRepository;
}
