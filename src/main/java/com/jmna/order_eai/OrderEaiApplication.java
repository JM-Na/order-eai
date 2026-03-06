package com.jmna.order_eai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OrderEaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderEaiApplication.class, args);
	}

}
