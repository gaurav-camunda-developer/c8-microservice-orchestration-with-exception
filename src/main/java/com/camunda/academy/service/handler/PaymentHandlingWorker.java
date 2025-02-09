package com.camunda.academy.service.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;

public class PaymentHandlingWorker {

	public void handlePaymentProcess(JobClient client, ActivatedJob job) {

		System.out.println("handlePaymentProcess Job Worker started...");
		try {
				boolean paymentFailed = true;
				if (paymentFailed) {
					System.out.println("Payment failed, throwing business error...");
					client.newThrowErrorCommand(job.getKey()).errorCode("CREDIT_CARD_PAYMENT_FAILED")
							.errorMessage("Payment processing failed due to insufficient funds").send().join();

					System.out.println("Retry Remaining...."+job.getRetries());
					return;
				}
				
				System.out.println("Failing job with key: " + job.getKey());
				
				client.newFailCommand(job.getKey()).retries(job.getRetries() - 1) // Reduce retry count
				.errorMessage("Payment failed, retrying...").send().join();


			//client.newCompleteCommand(job.getKey()).send().join();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
