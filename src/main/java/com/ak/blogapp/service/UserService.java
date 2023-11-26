package com.ak.blogapp.service;

import com.ak.blogapp.entity.User;
import com.ak.blogapp.payload.UserPayload;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUsername(String username);
    Optional<User> getByUsernameOrEmail(String usernameOrEmail);
    User createUser(UserPayload payload);
}
