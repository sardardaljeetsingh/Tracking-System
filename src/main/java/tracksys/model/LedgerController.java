package tracksys.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ledger")
public class LedgerController {

	@Autowired
	private LedgerRepository ledgerRepository;
	
	@Autowired
	private AccGroupRepository accGroupRepository;
	
	@RequestMapping(value ="/create",method = RequestMethod.POST)
	public @ResponseBody Ledger  create(@RequestBody Ledger ledger) {
		ledger.setAccGroup(accGroupRepository.findOne(ledger.getAccGroup().getId()));
		return ledgerRepository.save(ledger);
	}	
	
	@RequestMapping(value ="/findAll",method = RequestMethod.GET)
	public @ResponseBody Iterable<Ledger> findAll() {
		return ledgerRepository.findAll();
	}	
	
	@RequestMapping(value ="/find/{ledgerid}",method = RequestMethod.GET)
	public @ResponseBody Ledger findbyId(@PathVariable("ledgerid") int ledgerid) {
		return ledgerRepository.findOne(ledgerid);
	}	
	
}
