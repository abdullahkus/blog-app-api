package com.ak.blogapp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CommentResponse", description = "DTO for Comment response")
public class CommentResponse {
    @Schema(description = "Unique identifier for the comment", example = "1")
    private String id;

    @Schema(description = "Name of the commenter", example = "John Doe")
    private String name;

    @Schema(description = "Email of the commenter", example = "john@example.com")
    private String email;

    @Schema(description = "Body of the comment", example = "This is a great comment")
    private String body;

}