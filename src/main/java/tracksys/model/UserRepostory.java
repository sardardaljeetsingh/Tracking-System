package tracksys.model;

import org.springframework.data.repository.CrudRepository;

public interface UserRepostory extends CrudRepository<User, Integer> {

	User findByUsernameAndPassword(String username,String password);
}
