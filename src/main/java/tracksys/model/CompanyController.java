package tracksys.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/company")
public class CompanyController {

	  @RequestMapping(value ="/create",method = RequestMethod.POST)
	  
	  public @ResponseBody Company  create(@RequestBody Company company) {
	    	return companyRepository.save(company);
	  }
	  
	  @RequestMapping(value ="/getAll",method = RequestMethod.GET)
	  public ResponseEntity<Iterable<Company>> getAllCompanies() {
		  Iterable<Company> companyList = null;
	    try {
	    	companyList = companyRepository.findAll();
	    }
	    catch (Exception ex) {
		      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    }
		    return new ResponseEntity<Iterable<Company>>(companyList, HttpStatus.OK);
		  }  
	  
	  @RequestMapping(value ="/get/{companyId}",method = RequestMethod.GET)
	  public ResponseEntity<Company> findCompany(@PathVariable("companyId") String companyId) {
		  Company company = null;
		  try {
			  company = companyRepository.findOne(Integer.valueOf(companyId));
		  }
		  catch (Exception ex) {
			  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }
		  return new ResponseEntity<Company>(company, HttpStatus.OK);
	  } 
	  
	  @RequestMapping(value ="/find-by-userid/{userId}",method = RequestMethod.GET)
	  public @ResponseBody List<Company> findCompany(@PathVariable("userId") int userId) {
		  return companyRepository.findAllByUsers_Id(userId);
	  } 	  
	  
	  
	  @RequestMapping(value ="/{companyId}",method = RequestMethod.DELETE)
	  public ResponseEntity<Boolean> deleteCompany(@PathVariable("companyId") int companyId) {
		  try {
			  companyRepository.delete(companyId);
			  return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		  } catch (Exception ex) {
			  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		  }

	  } 	  
	  
	  @Autowired
	  private CompanyRepository companyRepository;
}
