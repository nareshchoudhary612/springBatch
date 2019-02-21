package springbatch_example.SpringBatchXmlToDatabase.DAOService;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import springbatch_example.SpringBatchXmlToDatabase.model.EntitlementEntity;

//@Repository
public interface EntitlementDAO extends CrudRepository<EntitlementEntity, Long> {
	
}
