package tracksys.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/agent")
public class AgentController {

	@Autowired
	private AgentRepository agentRepository;
	
	@PersistenceContext
	private EntityManager entityMng;
	
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody Agent  create(@RequestBody Agent agent) {
		return agentRepository.save(agent);
	}	
	
	@RequestMapping(value ="/update",method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Agent  update(@RequestBody Agent agent) {
		return agentRepository.save(entityMng.merge(agent));
	}		
	
	@RequestMapping(value ="/find/{agentId}",method = RequestMethod.GET)
	public @ResponseBody Agent findbyId(@PathVariable("agentId") int agentId) {
		return agentRepository.findOne(agentId);
	}
	
	@RequestMapping(value ="/findAll",method = RequestMethod.GET)
	public @ResponseBody Iterable<Agent> findAll() {
		return agentRepository.findAll();
	}
	
}
