package com.ak.blogapp.service.impl;

import com.ak.blogapp.entity.User;
import com.ak.blogapp.payload.UserPayload;
import com.ak.blogapp.repository.UserRepository;
import com.ak.blogapp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public Optional<User> getByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);
    }

    @Override
    @Transactional
    public User createUser(UserPayload payload) {
        User newUser = User.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .email(payload.getEmail())
                .password(passwordEncoder.encode(payload.getPassword()))
                .authorities(payload.getAuthorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        return userRepository.save(newUser);
    }

}
