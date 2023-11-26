package com.ak.blogapp.controller;

import com.ak.blogapp.payload.AuthLoginPayload;
import com.ak.blogapp.payload.AuthRegisterPayload;
import com.ak.blogapp.response.JWTAuthResponse;
import com.ak.blogapp.response.result.ErrorDetail;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthController", description = "Operations related to user authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "User login", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JWTAuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request (Validation Error)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PostMapping(value = {"/signin", "/login"})
    public ResponseEntity<SuccessBaseResponse<JWTAuthResponse>> login(
            @RequestBody @Valid @Parameter(description = "User login payload", required = true) AuthLoginPayload payload) {
        return ResponseEntity.ok(authService.login(payload));
    }

    @Operation(summary = "User registration", responses = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request (Validation Error)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PostMapping(value = {"/signup", "/register"})
    public ResponseEntity<SuccessBaseResponse<String>> register(
            @RequestBody @Valid @Parameter(description = "User registration payload", required = true) AuthRegisterPayload payload) {
        String response = authService.register(payload);
        return ResponseEntity.ok(SuccessBaseResponse.success(response));
    }
}
