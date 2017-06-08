package tracksys.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@RequestMapping(value ="/findAll",method = RequestMethod.GET)
	public @ResponseBody Iterable<Employee> findAll() {
		return employeeRepository.findAll();
	}		
	
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody Employee  create(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
}
