package springbatch_example.SpringBatchXmlToDatabase;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import springbatch_example.SpringBatchXmlToDatabase.DAOService.PortfolioWorkFileDAO;
import springbatch_example.SpringBatchXmlToDatabase.model.DiaryEntity;
import springbatch_example.SpringBatchXmlToDatabase.model.EntitlementEntity;
import springbatch_example.SpringBatchXmlToDatabase.model.PortfolioWorkFileEntity;
	 
	public class entGenProcessor implements ItemProcessor<DiaryEntity, List<EntitlementEntity>>
	{
		@Autowired
		PortfolioWorkFileDAO portfolioWorkFileDAO;
		 
		
		@Override
		public List<EntitlementEntity> process(DiaryEntity item) throws Exception {
			
			List<PortfolioWorkFileEntity> pfwfs = portfolioWorkFileDAO.findByCompany(item.getCompanyName());
			
			List<EntitlementEntity> eeList = new ArrayList<EntitlementEntity>();
			System.out.println("number of pfwf files of company :"+ item.getCompanyName() + " is " + pfwfs.size());
			for(int i =0; i<pfwfs.size(); i++) {
				float conversion_rate =1;
				
				EntitlementEntity entitlementEntity= new EntitlementEntity();
				
				entitlementEntity.setDiaryId(item.getDiaryNo());
				System.out.println(entitlementEntity.getDiaryId());
				
				entitlementEntity.setCustomerId(pfwfs.get(i).getCustomerId());
				System.out.println(entitlementEntity.getCustomerId());
				
				entitlementEntity.setAccountId(pfwfs.get(i).getAccountID());
				System.out.println(entitlementEntity.getAccountId());
				
				entitlementEntity.setPortfolioId(pfwfs.get(i).getPortfolioWfId());
				System.out.println(entitlementEntity.getPortfolioId());
				
				entitlementEntity.setPayCurrency(item.getCurrency());			
				System.out.println(entitlementEntity.getPayCurrency());
				
				entitlementEntity.setAccountCurrency(pfwfs.get(i).getAccountCurrency());
				System.out.println(entitlementEntity.getAccountCurrency());
				
				entitlementEntity.setUnits(pfwfs.get(i).getUnits());
				System.out.println(entitlementEntity.getUnits());
				
				entitlementEntity.setPerUnitAmount(item.getAmount());
				System.out.println(entitlementEntity.getPerUnitAmount());
				
				if(entitlementEntity.getPayCurrency() != entitlementEntity.getAccountCurrency()) {
					
				}
				
				
				eeList.add(entitlementEntity);
			}
			
		//	System.out.println(ee.size());
			return eeList;
		}
	}

