package com.ak.blogapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(name = "CommentPayload", description = "Payload for Comment")
public class CommentPayload {
    @Schema(description = "Name of the commenter", example = "John Doe", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 250, message = "{name.size}")
    @NotEmpty(message = "{name.empty}")
    private String name;

    @Schema(description = "Email of the commenter", example = "john@example.com", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 250, message = "{email.size}")
    @NotEmpty(message = "{email.empty}")
    @Email(message = "{email.email}")
    private String email;

    @Schema(description = "Body of the comment", example = "This is a great comment", minimum = "10", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{body.empty}")
    @Size(min = 10, max = 250, message = "{body.size}")
    private String body;
}
