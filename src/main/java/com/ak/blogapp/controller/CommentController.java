package com.ak.blogapp.controller;

import com.ak.blogapp.payload.CommentPayload;
import com.ak.blogapp.response.CommentResponse;
import com.ak.blogapp.response.result.ErrorDetail;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1")
@Tag(name = "CommentController", description = "Operations related to comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Create a new comment", responses = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request (Validation Error)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
    })
    @PreAuthorize("permitAll()")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<SuccessBaseResponse<CommentResponse>> createComment(
            @Parameter(description = "ID of the post")
            @PathVariable(value = "postId") String postId,
            @Valid @RequestBody CommentPayload commentPayload
    ) {
        return new ResponseEntity<>(
                commentService.createComment(postId, commentPayload),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get comments by post ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found (Comment not found)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
    })
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<SuccessBaseResponse<List<CommentResponse>>> getCommentsByPostId(
            @Parameter(description = "ID of the post")
            @PathVariable(value = "postId") String postId
    ) {
        return new ResponseEntity<>(
                commentService.getCommentsByPostId(postId),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get comment by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found (Comment not found)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SuccessBaseResponse<CommentResponse>> getCommentById(
            @Parameter(description = "ID of the post")
            @PathVariable(value = "postId") String postId,
            @Parameter(description = "ID of the comment")
            @PathVariable(value = "commentId") String commentId
    ) {
        return new ResponseEntity<>(
                commentService.getCommentById(postId, commentId),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update a comment", responses = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request (Validation Error)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found (Comment not found)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PreAuthorize("permitAll()")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SuccessBaseResponse<CommentResponse>> updateComment(
            @Parameter(description = "ID of the post")
            @PathVariable(value = "postId") String postId,
            @Parameter(description = "ID of the comment")
            @PathVariable(value = "commentId") String commentId,
            @Valid @RequestBody CommentPayload commentPayload
    ) {
        return new ResponseEntity<>(
                commentService.updateComment(postId, commentId, commentPayload),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete a comment", responses = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not authorized)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found (Comment not found)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SuccessBaseResponse<String>> deleteComment(
            @Parameter(description = "ID of the post")
            @PathVariable(value = "postId") String postId,
            @Parameter(description = "ID of the comment")
            @PathVariable(value = "commentId") String commentId
    ) {
        return new ResponseEntity<>(
                commentService.deleteComment(postId, commentId),
                HttpStatus.OK
        );
    }
}
