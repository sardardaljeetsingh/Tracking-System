package tracksys.model;

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
	
	@RequestMapping(value ="/createprev",method = RequestMethod.POST)
	public @ResponseBody User  createprev(@RequestBody User user) {
		Previliges previliges = user.getPreviliges();
		previliges.setUser(user);
		userPreviligeReposotory.save(previliges);
		return previliges.getUser();
	}
	
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody User  create(@RequestBody User user) {
		user.setPhone(123456);
		user.getPreviliges().setUser(user);
		userRepostory.save(user);
		return user;
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
	  
}
