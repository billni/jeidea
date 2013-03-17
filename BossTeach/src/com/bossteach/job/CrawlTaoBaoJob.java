package com.bossteach.job;

import com.bossteach.job.service.TaoBaoTransactionService;

public abstract class CrawlTaoBaoJob {
	public TaoBaoTransactionService 	taoBaoTransactionService;
	
	public TaoBaoTransactionService getTaoBaoTransactionService() {
		return taoBaoTransactionService;
	}
	public void setTaoBaoTransactionService(
			TaoBaoTransactionService taoBaoTransactionService) {
		this.taoBaoTransactionService = taoBaoTransactionService;
	}
}
