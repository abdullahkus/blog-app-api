package com.ak.blogapp.payload;

import com.ak.blogapp.enums.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(name = "RolePayload", description = "Payload for User Role")
public class RolePayload {
    @Schema(description = "Name of the user role", example = "USER", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{name.empty}")
    @Size(min = 2, max = 250, message = "{name.size}")
    private ERole name;
}