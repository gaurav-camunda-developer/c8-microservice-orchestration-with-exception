package com.camunda.academy;

import java.time.Duration;

import com.camunda.academy.service.handler.CreditCardServiceHandler;
import com.camunda.academy.service.handler.PaymentsServiceHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

public class PaymentProcess {

	private static final String ZEEBE_ADDRESS = "bb0a90ce-ec78-4d96-a50d-b55de1b589f2.syd-1.zeebe.camunda.io:443";
	private static final String ZEEBE_CLIENT_ID = "wQb~SVdkTymoQ5oTY-v1nR1wYwy-7VQO";
	private static final String ZEEBE_CLIENT_SECRET = "3VYjCR5dUFRO8ksiSfAUdiKosMK1ljOl1JlvVXiKb4YLFMEOA-Lh7AP-SeNhs3zo";
	private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
	private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";

	public static void main(String[] args) {
		OAuthCredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
				.authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
				.audience(ZEEBE_TOKEN_AUDIENCE)
				.clientId(ZEEBE_CLIENT_ID)
				.clientSecret(ZEEBE_CLIENT_SECRET)
				.build();

		try (final ZeebeClient client = ZeebeClient.newClientBuilder()
				.gatewayAddress(ZEEBE_ADDRESS)
				.credentialsProvider(credentialsProvider)
				.build()) {
			System.out.println("Connected to.."+client.newTopologyRequest().send().join());
			System.out.println("Opening job worker.");

            try (final JobWorker workerRegistration =
                client
                    .newWorker()
                    .jobType("chargeCreditCard").handler(new CreditCardServiceHandler())
                    .timeout(Duration.ofSeconds(10))
                    .open()) {
                System.out.println("Job worker opened and receiving jobs.");
                // run until System.in receives exit command
                //waitUntilSystemInput("exit");
                Thread.sleep(10000);
                
                
            }
            
            try (final JobWorker workerRegistration =
                    client
                        .newWorker()
                        .jobType("chargeCreditCard").handler(new PaymentsServiceHandler())
                        .timeout(Duration.ofSeconds(10))
                        .open()) {
                    System.out.println("Job worker opened and receiving jobs.");
                    // run until System.in receives exit command
                    //waitUntilSystemInput("exit");
                    Thread.sleep(10000);
                    
                    
                }	
                
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}


}
