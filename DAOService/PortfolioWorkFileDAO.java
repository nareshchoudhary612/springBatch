package springbatch_example.SpringBatchXmlToDatabase.DAOService;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import springbatch_example.SpringBatchXmlToDatabase.model.PortfolioWorkFileEntity;

public interface PortfolioWorkFileDAO extends CrudRepository<PortfolioWorkFileEntity, Long>{
	
	List<PortfolioWorkFileEntity> findByCompany(String company);
	
	
}
