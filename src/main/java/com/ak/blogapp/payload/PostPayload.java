package com.ak.blogapp.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(name = "PostPayload", description = "Payload for Post")
public class PostPayload {
    @Schema(description = "Title of the post", example = "Sample Post", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{title.empty}")
    @Size(min = 2, max = 250, message = "{title.size}")
    private String title;

    @Schema(description = "Description of the post", example = "This is a sample post", minimum = "2", maximum = "250", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{description.empty}")
    @Size(min = 2, max = 250, message = "{description.size}")
    private String description;

    @Schema(description = "Content of the post", example = "Lorem ipsum dolor sit amet", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "{content.empty}")
    private String content;
}
