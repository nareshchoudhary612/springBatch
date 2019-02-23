package springbatch_example.SpringBatchXmlToDatabase;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.assertj.core.internal.Lists;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import springbatch_example.SpringBatchXmlToDatabase.model.EntitlementEntity;

public class EntGenWriter implements ItemWriter<List<EntitlementEntity>> {
		
	@Autowired
	 EntityManagerFactory emf;
		   

		/*   public EntGenWriter(JpaItemWriter jpaItemWriter) {
		      this. jpaItemWriter = jpaItemWriter;
		   }*/

	/*	   @Override  
		   public void write(List<? extends Lists<EntitlementEntity>> items) {  //not sure which lists
		     for(List<EntitlementEntity> o : items) {
		       this. jpaItemWriter.write(o);
		     }
		   }*/

		@Override
		public void write(List<? extends List<EntitlementEntity>> items) throws Exception {
			System.out.println("now in writer" + items.size()+ "size of list within list"+ items.get(0).size());
			System.out.println("****************************************8");
			for(List<EntitlementEntity> z :items) {
				z.get(0).getAccountCurrency();
				for(EntitlementEntity ee: z) {
					System.out.println(ee.getAccountCurrency());
				}
			}
			for(List<EntitlementEntity> o : items) {
				JpaItemWriter<EntitlementEntity> jpaItemWriter = new JpaItemWriter<>();
				jpaItemWriter.setEntityManagerFactory(emf);
				jpaItemWriter.write(o);
			    
			 /*   private JpaItemWriter jpaItemWriter;	
				this. jpaItemWriter.setEntityManagerFactory(emf);*/
			    
			 
			}
		}
		
}
/*JpaItemWriter writer = new JpaItemWriter();
writer.setEntityManagerFactory(emf);
return writer;*/