package springbatch_example.SpringBatchXmlToDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import springbatch_example.SpringBatchXmlToDatabase.DAOService.EntitlementDAO;
import springbatch_example.SpringBatchXmlToDatabase.model.EntitlementEntity;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
 
 @Autowired
 public JobBuilderFactory jobBuilderFactory;
 
 @Autowired
 public StepBuilderFactory stepBuilderFactory;
 
 @Autowired
 public DataSource dataSource;
 
 @Autowired
 EntityManagerFactory emf;
 
 @Autowired
 EntitlementDAO entitlementDAO;
 
 
 /**************Not required after adding JPA******************************/
 /*@Bean
 public DataSource dataSource() {
  final DriverManagerDataSource dataSource = new DriverManagerDataSource();
  dataSource.setDriverClassName("com.mysql.jdbc.Driver");
  //dataSource.setUrl("jdbc:mysql://localhost/springbatch");
  dataSource.setUrl("jdbc:mysql://localhost:3306/entitlementdb");
  dataSource.setUsername("root");
  dataSource.setPassword("Atul182");
  
  return dataSource;
 }*/
 /*************************************************************************/
 
 
 /*************************************************************************/
 // 				READER	
 /*************************************************************************/
 @Bean
 public StaxEventItemReader<EntitlementEntity> reader(){
  StaxEventItemReader<EntitlementEntity> reader = new StaxEventItemReader<EntitlementEntity>();
  reader.setResource(new ClassPathResource("ee.xml"));
  reader.setFragmentRootElementName("EntitlementEntity");
  
  Map<String, String> aliases = new HashMap<String, String>();
  aliases.put("EntitlementEntity", "springbatch_example.SpringBatchXmlToDatabase.model.EntitlementEntity");
  
  XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
  xStreamMarshaller.setAliases(aliases);
  
  reader.setUnmarshaller(xStreamMarshaller);
  
  return reader;
 }
 
 /*************************************************************************/
 // 				PROCESSOR	
 /*************************************************************************/
 @Bean
 public ItemProcessor<EntitlementEntity, EntitlementEntity> processor() {
     return (item) -> {
         item.setDiaryId(item.getDiaryId() +1); 
         return item;
     };
 }
 
 /*************************************************************************/
 // 				WRITER	
 /*************************************************************************/
 // Removing after adding JPA
 /*@Bean
 public JdbcBatchItemWriter<EntitlementEntity> writer(){
  JdbcBatchItemWriter<EntitlementEntity> writer = new JdbcBatchItemWriter<EntitlementEntity>();
  writer.setDataSource(dataSource);
  writer.setSql("insert into entitlement(entitlement_id,customer_id) values(?, ?)");
  writer.setItemPreparedStatementSetter(new EntitlementEntityItemPreparedStmSetter());
  return writer;
  
  private class EntitlementEntityItemPreparedStmSetter implements ItemPreparedStatementSetter<EntitlementEntity>{

  @Override
  public void setValues(EntitlementEntity entitlementEntity, PreparedStatement ps) throws SQLException {
   ps.setInt(1, entitlementEntity.getEntitlementId());
   ps.setString(2, entitlementEntity.getCustomerId());
  }
 }*/
 
 
 @Bean
 public JpaItemWriter writer() {
     JpaItemWriter writer = new JpaItemWriter();
     writer.setEntityManagerFactory(emf);
     return writer;
 }
 
 
 
 /*************************************************************************/
 // 				BATCH JOB RUN	
 /*************************************************************************/
 @Bean
 public Step step1() {
  return stepBuilderFactory.get("step1")
    .<EntitlementEntity, EntitlementEntity> chunk(10)
    .reader(reader())
    .processor(processor())
    .writer(writer())
    .build();
 }
 
 @Bean
 public Job importEntitlementJob() {   
  return jobBuilderFactory.get("importEntitlementJob")
    .incrementer(new RunIdIncrementer())
    .flow(step1())
    .end()
    .build();
    
 }
}
