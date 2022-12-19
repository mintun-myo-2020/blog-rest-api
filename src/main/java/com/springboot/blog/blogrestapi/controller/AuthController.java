package com.springboot.blog.blogrestapi.controller;

import com.springboot.blog.blogrestapi.DTO.JwtAuthResponse;
import com.springboot.blog.blogrestapi.DTO.LoginDTO;
import com.springboot.blog.blogrestapi.DTO.SignupDTO;
import com.springboot.blog.blogrestapi.Security.JwtTokenProvider;
import com.springboot.blog.blogrestapi.model.Role;
import com.springboot.blog.blogrestapi.model.User;
import com.springboot.blog.blogrestapi.repository.RoleRepository;
import com.springboot.blog.blogrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        get token from tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupDTO){

        if (userRepository.existsByUsername(signupDTO.getUsername())) {
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signupDTO.getEmail())){
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setName(signupDTO.getName());
        user.setUsername(signupDTO.getUsername());
        String encodedPassword = passwordEncoder.encode(signupDTO.getPassword());
        user.setPassword(encodedPassword);

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);
        return new ResponseEntity<>("Successful registration", HttpStatus.CREATED);
    }

}
