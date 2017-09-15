package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AccGroupRepository extends CrudRepository<AccGroup, Integer> {

	public List<AccGroup> findAllByCompany(Company company);
	
	//public List<Group> findAllByCompanyAndParent(Company company, int parentGrpId);
	
	public List<AccGroup> findAllByParent(int parentGrpId);
	

}
