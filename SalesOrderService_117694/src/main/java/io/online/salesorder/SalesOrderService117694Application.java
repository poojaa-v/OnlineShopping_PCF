package io.online.salesorder;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.online.salesorder.controller.SalesOrderController;
import io.online.salesorder.domain.Customer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class SalesOrderService117694Application {

	@Autowired
	private SalesOrderController salesOrderController;

	public static void main(String[] args) {
		SpringApplication.run(SalesOrderService117694Application.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RabbitListener(queues = "CustomerCreated")
	public void receiveMessage(Customer customer) {
		System.out.println("Customer Details" + customer.getId());
		salesOrderController.insertCustomerSOS(customer);

	}

}
