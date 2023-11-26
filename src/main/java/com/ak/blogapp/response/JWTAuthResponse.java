package com.ak.blogapp.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "JWTAuthResponse", description = "JWT Authentication Response")
public class JWTAuthResponse {
    @Schema(description = "Access token for authentication", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessToken;

    @Schema(description = "Token type, usually 'Bearer'", defaultValue = "Bearer", requiredMode = Schema.RequiredMode.AUTO)
    private String tokenType = "Bearer";
}