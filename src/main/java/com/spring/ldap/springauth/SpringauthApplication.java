package com.spring.ldap.springauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@SpringBootApplication
public class SpringauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringauthApplication.class, args);
	}

}
