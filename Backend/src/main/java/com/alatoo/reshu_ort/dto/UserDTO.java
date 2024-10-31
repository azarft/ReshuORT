package com.alatoo.reshu_ort.dto;


import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    private LocalDateTime createdAt;

}
