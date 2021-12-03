package com.enesuguroglu;

import com.enesuguroglu.Producer.KafkaProducer;
import com.enesuguroglu.Services.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonPosGeneratorApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(JsonPosGeneratorApplication.class, args);
	}

	@Autowired
	private KafkaProducer kafkaProducer;

	@Autowired
	private InvoiceGenerator invoiceGenerator;

	@Value("{$application.configs.invoice.count}")
	private int INVOICE_COUNT;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		for (int i = 0; i < INVOICE_COUNT; i++){
			kafkaProducer.sendMessage(invoiceGenerator.getNextInvoice());
			Thread.sleep(1000);
		}
	}

}
