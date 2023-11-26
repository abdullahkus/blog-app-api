package com.ak.blogapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(name = "AuthRegisterPayload", description = "Payload for User Registration")
public class AuthRegisterPayload {
    @Schema(description = "First name of the user", example = "John", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{firstName.empty}")
    @Size(min = 2, max = 250, message = "{firstName.size}")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{lastName.empty}")
    @Size(min = 2, max = 250, message = "{lastName.size}")
    private String lastName;

    @Schema(description = "Username of the user", example = "john_doe", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{username.empty}")
    @Size(min = 2, max = 250, message = "{username.size}")
    private String username;

    @Schema(description = "Email of the user", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{email.empty}")
    @Size(min = 2, max = 250, message = "{email.size}")
    @Email(message = "{email.email}")
    private String email;

    @Schema(description = "Password of the user", example = "StrongPassword123", minimum = "8", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{password.empty}")
    @Size(min = 8, max = 250, message = "{password.size}")
    private String password;
}