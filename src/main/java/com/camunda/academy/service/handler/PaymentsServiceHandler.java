package com.camunda.academy.service.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class PaymentsServiceHandler implements JobHandler{
	
	PaymentHandlingWorker paymentHandling = new PaymentHandlingWorker();

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		
		paymentHandling.handlePaymentProcess(client, job);
		client.newCompleteCommand(job.getKey()).send().join();
		
	}
	
	


}
