package com.jloka.jloka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jloka.jloka.models.Customer;
import com.jloka.jloka.repositories.CustomerRepository;


@SpringBootApplication
public class JlokaApplication {

	private static final Logger logger = LoggerFactory.getLogger(JlokaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JlokaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository) {
		return (args) -> {
			// save customers
			// customerRepository.save(new Customer("JLoka-01", "JLoka-01"));
			// customerRepository.save(new Customer("JLoka-01", "JLoka-01"));
			// customerRepository.save(new Customer("JLoka-01", "JLoka-01"));
			// customerRepository.save(new Customer("JLoka-01", "JLoka-02"));
			// customerRepository.save(new Customer("JLoka-01", "JLoka-03"));

			// fetch all customers
			logger.info("==================================");
			logger.info("Getting all customer data");
			customerRepository.findAll().forEach(customer -> {
				logger.info(customer.toString());
			});
			
			logger.info("==================================");
			logger.info("Getting customer by id");
			Customer customer = customerRepository.findById(1L);
			logger.info(customer.toString());

			logger.info("==================================");
			logger.info("Getting customers by last name: JLoka-01");
			customerRepository.findByLastName("JLoka-01").forEach(customer02 -> {
				logger.info(customer02.toString());
			});
		};
	}
}
