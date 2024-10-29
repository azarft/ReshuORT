package com.alatoo.reshu_ort;

import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReshuOrtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReshuOrtApplication.class, args);
	}

}
