package com.bossteach.job.service;

import java.util.List;
import com.bossteach.model.HongKangInsuranceTransaction;
import com.google.appengine.api.datastore.Key;

public interface TaoBaoTransactionService {
	/**
	 * insert and update HongKangInsuranceTransaction to db
	 * @param object
	 */
	public void createHongKangInsuranceTransaction(HongKangInsuranceTransaction hongKangInsuranceTransaction);
		
	/**
	 * find an HongKangInsuranceTransaction from db
	 * @param entity
	 * @param key
	 */
	public void findHongKangInsuranceTransaction(Key key);
		
	/**
	 * remove HongKangInsuranceTransactions from db
	 * @param hongKangInsuranceTransaction
	 */
	public void deleteHongKangInsuranceTransaction(HongKangInsuranceTransaction hongKangInsuranceTransaction);
	
	/**
	 * list HongKangInsuranceTransactions
	 */
	public List<HongKangInsuranceTransaction> listHongKangInsuranceTransaction();	
}
