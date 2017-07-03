package tracksys.model;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

	//public List<Company> findAllByUsers_Id(int userid);
}
