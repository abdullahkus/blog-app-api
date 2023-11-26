package com.ak.blogapp.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PostResponse", description = "DTO for Post response")
public class PostResponse {
    @Schema(description = "Unique identifier for the post", example = "1")
    private String id;

    @Schema(description = "Title of the post", example = "Sample Post")
    private String title;

    @Schema(description = "Description of the post", example = "This is a sample post")
    private String description;

    @Schema(description = "Content of the post", example = "Lorem ipsum dolor sit amet")
    private String content;

    @Schema(description = "Comments associated with the post")
    private Set<CommentResponse> comments;
}
