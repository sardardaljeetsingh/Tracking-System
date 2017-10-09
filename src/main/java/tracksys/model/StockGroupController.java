package tracksys.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	  
	/**
	Method for search
	*/
	@RequestMapping(value ="/find-by-name-search/{companyId}/{stockGroupName}",method = RequestMethod.GET)
	public ResponseEntity<Iterable<StockGroup>> findByNameSearch(@PathVariable("companyId") int companyId, @PathVariable("stockGroupName") String stockGroupName) {
		Iterable<StockGroup> grpLableList = null;
		try {
			Company company = new Company(companyId) ;
			grpLableList = groupRepository.findByCompanyAndNameIgnoreCaseContaining(company,stockGroupName);
		}
		catch (Exception ex) {
			ex.printStackTrace();
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
	  
		@RequestMapping(value ="/delete/{groupId}",method = RequestMethod.DELETE)
		@Transactional
		public @ResponseBody boolean  delete(@PathVariable("groupId") int groupId) {
			groupRepository.delete(groupId);
			return true;
		}	  
	
	  @Autowired
	  private StockGroupRepository groupRepository;
	  
	  @PersistenceContext
	private EntityManager entityMng;
}
