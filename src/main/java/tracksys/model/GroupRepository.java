package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {

	public List<Group> findAllByCompany(Company company);
	
	//public List<Group> findAllByCompanyAndParent(Company company, int parentGrpId);
	
	public List<Group> findAllByParent(int parentGrpId);
}
