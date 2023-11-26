package com.ak.blogapp.payload;

import com.ak.blogapp.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Set;

@Getter
@Schema(name = "UserPayload", description = "Payload for User")
public class UserPayload {
    @Schema(description = "First name of the user", example = "John", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{firstName.empty}")
    @Size(min = 2, max = 250, message = "{firstName.size}")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{lastName.empty}")
    @Size(min = 2, max = 250, message = "{lastName.size}")
    private String lastName;

    @Schema(description = "Email of the user", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{email.empty}")
    @Email(message = "{email.email}")
    private String email;

    @Schema(description = "Password of the user", example = "StrongPassword123", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{password.empty}")
    @Size(min = 8, max = 250, message = "{password.size}")
    private String password;

    @Schema(description = "Roles/Authorities of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{authorities.empty}")
    private Set<Role> authorities;
}
