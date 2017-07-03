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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserPreviligeReposotory userPreviligeReposotory;
	@Autowired
	private UserRepostory userRepostory;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@RequestMapping(value ="/createprev",method = RequestMethod.POST)
	public @ResponseBody List<Previliges>  createprev(@RequestBody List<Previliges> previliges) {
		for(Previliges previlige : previliges ){
			previlige.setCompany(companyRepository.findOne(previlige.getCompany().getId()));
			previlige.setUser(userRepostory.findOne(previlige.getUser().getId()));
		}
		userPreviligeReposotory.save(previliges);
		return previliges;
	}
	
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody User  create(@RequestBody User user) {
		//user.setPhone(123456);
		//user.getPreviliges().setUser(user);
		return userRepostory.save(user);
		//return user;
	}	
	
	@RequestMapping(value ="/find/{userId}",method = RequestMethod.GET)
	public @ResponseBody User findbyId(@PathVariable("userId") int userId) {
		return userRepostory.findOne(userId);
	}
	
	@RequestMapping(value ="/findAll",method = RequestMethod.GET)
	public @ResponseBody Iterable<User> findAll() {
		return userRepostory.findAll();
	}	
	
	@RequestMapping(value ="/findAllPrev",method = RequestMethod.GET)
	public @ResponseBody Iterable<Previliges> findAllPrev() {
		return userPreviligeReposotory.findAll();
	}
	
	@RequestMapping(value ="/findAllPrev/{userId}",method = RequestMethod.GET)
	public @ResponseBody Iterable<Previliges> findAllPrevByUser(@PathVariable("userId") int userId) {
		return userPreviligeReposotory.findAllByUser_Id(userId);
	}	
	
	
	@RequestMapping(value ="/findPrevByUserAndCompany/{userId}/{companyid}",method = RequestMethod.GET)
	public @ResponseBody Previliges findPrevByUserAndCompany(@PathVariable("userId") int userId,
			@PathVariable("companyid") int companyid) {
		return userPreviligeReposotory.findByUser_IdAndCompany_Id(userId, companyid);
	}		
	
	@RequestMapping(value ="/login",method = RequestMethod.POST)
	public @ResponseBody User  login(@RequestBody User user) {
		return userRepostory.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	  
}
