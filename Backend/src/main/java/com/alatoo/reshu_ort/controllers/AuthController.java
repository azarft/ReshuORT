package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.UserDTO;
import com.alatoo.reshu_ort.dto.authorization.*;
import com.alatoo.reshu_ort.dto.authorization.AuthRegistrationDTO;
import com.alatoo.reshu_ort.entities.RefreshToken;
import com.alatoo.reshu_ort.entities.User;
import com.alatoo.reshu_ort.services.JwtService;
import com.alatoo.reshu_ort.services.RefreshTokenService;
import com.alatoo.reshu_ort.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;


    @PostMapping("/register")
    public UserDTO register(@Validated @RequestBody AuthRegistrationDTO authRegistrationDTO){
        return userService.register(authRegistrationDTO);
    }

    @PostMapping("/login")
    public JwtTokenDto AuthenticateAndGetToken(@RequestBody AuthLoginDto authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("Refresh Token sent to user");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return JwtTokenDto.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
    @PostMapping("/refreshToken")
    public JwtTokenDto refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtTokenDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in Database!!"));
    }

}
