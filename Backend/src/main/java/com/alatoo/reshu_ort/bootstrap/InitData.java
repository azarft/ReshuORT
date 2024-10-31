package com.alatoo.reshu_ort.bootstrap;

import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
//        User user = User.builder()
//                .lastName("Azanovaza")
//                .firstName("Argena")
//                .username("azarft1a")
//                .email("admin1@gmailaa.com")
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        userRepository.save(user);
    }
}
