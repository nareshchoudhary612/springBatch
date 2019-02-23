package springbatch_example.SpringBatchXmlToDatabase.DAOService;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import springbatch_example.SpringBatchXmlToDatabase.model.DiaryEntity;

@Repository
public interface DiaryDAO extends CrudRepository<DiaryEntity, Long> {
	
	List<DiaryEntity> findByStatus(String status);
	
	List<DiaryEntity> findByDiaryNo(int diaNo);
	

}
