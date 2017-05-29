package tracksys.model;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class TestController {

	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public User  create(@RequestBody User user) {
		user.setPhone(123456);
		return user;
	}	
}
