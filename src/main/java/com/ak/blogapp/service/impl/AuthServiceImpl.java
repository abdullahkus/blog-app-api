package com.ak.blogapp.service.impl;

import com.ak.blogapp.entity.Role;
import com.ak.blogapp.entity.User;
import com.ak.blogapp.enums.ERole;
import com.ak.blogapp.exception.ResourceNotFoundException;
import com.ak.blogapp.payload.AuthLoginPayload;
import com.ak.blogapp.payload.AuthRegisterPayload;
import com.ak.blogapp.repository.RoleRepository;
import com.ak.blogapp.repository.UserRepository;
import com.ak.blogapp.response.JWTAuthResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.security.JwtTokenProvider;
import com.ak.blogapp.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public SuccessBaseResponse<JWTAuthResponse> login(AuthLoginPayload authLoginPayload) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginPayload.getUsernameOrEmail(),
                        authLoginPayload.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return SuccessBaseResponse.success(new JWTAuthResponse(token, "Bearer"));
    }

    @Override
    @Transactional
    public String register(AuthRegisterPayload payload) {
        if (userRepository.existsByUsername(payload.getUsername())) {
            throw new ResourceNotFoundException("User", "Username", payload.getUsername());
        }

        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new ResourceNotFoundException("User", "Email", payload.getEmail());
        }

        Role role = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Role", ERole.ROLE_USER.name()));


        User newUser = User.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .username(payload.getUsername())
                .password(passwordEncoder.encode(payload.getPassword()))
                .authorities(Set.of(role))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        userRepository.save(newUser);
        return "User registered successfully";
    }
}

