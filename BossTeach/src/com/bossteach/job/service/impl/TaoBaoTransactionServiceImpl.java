package com.bossteach.job.service.impl;

import java.util.List;
import com.bossteach.core.spring.daosupport.DaoTemplate;
import com.bossteach.job.service.TaoBaoTransactionService;
import com.bossteach.model.HongKangInsuranceTransaction;
import com.google.appengine.api.datastore.Key;

public class TaoBaoTransactionServiceImpl extends DaoTemplate implements TaoBaoTransactionService {
	
	public void createHongKangInsuranceTransaction(HongKangInsuranceTransaction hongKangInsuranceTransaction){
		getDaoTemplate().persist(hongKangInsuranceTransaction);
	}
		
	public void findHongKangInsuranceTransaction(Key key){
		getDaoTemplate().find(HongKangInsuranceTransaction.class, key);
	}	
	
	public void deleteHongKangInsuranceTransaction(HongKangInsuranceTransaction hongKangInsuranceTransaction){
		getDaoTemplate().remove(hongKangInsuranceTransaction);
	}

	/**
	 * list messages
	 */
	public List listHongKangInsuranceTransaction() {				
		return getDaoTemplate().getEntityManagerFactory().createEntityManager().
				createNamedQuery("listHongKangInsuranceTransaction").getResultList();				
	}
}
