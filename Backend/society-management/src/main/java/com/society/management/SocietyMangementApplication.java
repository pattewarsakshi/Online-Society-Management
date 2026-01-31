package com.society.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(
	    exclude = {
	        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
	    }
	)
	public class SocietyMangementApplication {

	    public static void main(String[] args) {
	        SpringApplication.run(SocietyMangementApplication.class, args);
	    }
	}

