package com.alatoo.reshu_ort.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime createdAt;

}
