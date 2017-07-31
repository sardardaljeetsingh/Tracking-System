package tracksys.model;

import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, Integer> {

	Agent findByName(String name);
}
