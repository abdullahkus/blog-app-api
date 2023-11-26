package com.ak.blogapp.service;

import com.ak.blogapp.payload.AuthLoginPayload;
import com.ak.blogapp.payload.AuthRegisterPayload;
import com.ak.blogapp.response.JWTAuthResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;

public interface AuthService {
    SuccessBaseResponse<JWTAuthResponse> login(AuthLoginPayload payload);
    String register(AuthRegisterPayload payload);
}
