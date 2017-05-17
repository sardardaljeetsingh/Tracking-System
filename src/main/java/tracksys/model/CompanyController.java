package tracksys.model;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyRepository companyRepository;
	
	@RequestMapping(path="/findAll", method = RequestMethod.GET)
	public Iterable<Company> findByRepo() throws IOException {
		return companyRepository.findAll();
	}
	
	@RequestMapping(value = "/get/{companyid}", method = RequestMethod.GET)
	public Company saveByRepo(@PathVariable String companyid) {
		return companyRepository.findOne(Integer.valueOf(companyid));
	}
}
