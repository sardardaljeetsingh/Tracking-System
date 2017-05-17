package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {

	public List<Group> findAllByCompanyid(int companyId);
	
	public List<Group> findAllByCompanyidAndParent(int companyId, int parentGrpId);
	
	public List<Group> findAllByParent(int parentGrpId);
}
