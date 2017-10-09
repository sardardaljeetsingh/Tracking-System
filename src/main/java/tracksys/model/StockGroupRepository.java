package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StockGroupRepository extends CrudRepository<StockGroup, Integer> {

	public List<StockGroup> findAllByCompany(Company company);
	
	//public List<Group> findAllByCompanyAndParent(Company company, int parentGrpId);
	
	public List<StockGroup> findAllByParent(int parentGrpId);
	
	
	public List<StockGroup> findByCompanyAndNameIgnoreCaseContaining(Company company,String name);
	
	
}
