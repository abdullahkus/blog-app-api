package com.ak.blogapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(name = "AuthLoginPayload", description = "Payload for User Authentication")
public class AuthLoginPayload {
    @Schema(description = "Username or email of the user", example = "john.doe@example.com", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{usernameOrEmail.empty}")
    @Size(min = 2, max = 250, message = "{usernameOrEmail.size}")
    private String usernameOrEmail;

    @Schema(description = "Password of the user", example = "StrongPassword123", minimum = "8", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{password.empty}")
    @Size(min = 8, max = 250, message = "{password.size}")
    private String password;
}