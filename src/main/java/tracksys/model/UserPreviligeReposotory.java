package tracksys.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserPreviligeReposotory extends CrudRepository<Previliges, Integer> {
	public List<Previliges> findAllByUser_Id(int userid);
	public Previliges findByUser_IdAndCompany_Id(int userid,int companyId);
}
