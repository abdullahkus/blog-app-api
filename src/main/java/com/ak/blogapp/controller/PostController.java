package com.ak.blogapp.controller;

import com.ak.blogapp.enums.EPostSortBy;
import com.ak.blogapp.exception.InvalidEnumValueException;
import com.ak.blogapp.payload.PostPayload;
import com.ak.blogapp.response.PostResponse;
import com.ak.blogapp.response.result.ErrorDetail;
import com.ak.blogapp.response.result.PaginationResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.service.PostService;
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

import java.util.Locale;

import static com.ak.blogapp.utils.AppConstants.*;

@RestController
@RequestMapping("api/v1/posts")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "PostController", description = "Operations related to posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Create a new post", responses = {
            @ApiResponse(responseCode = "201", description = "Post created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request (Validation Error)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not authorized)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SuccessBaseResponse<PostResponse>> createPost(@Valid @RequestBody PostPayload postPayload) {
        return new ResponseEntity<>(
                postService.createPost(postPayload),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get all posts", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request (Invalid Sort Field)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
    })
    @GetMapping
    public ResponseEntity<SuccessBaseResponse<PaginationResponse<PostResponse>>> getAllPosts(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @Parameter(description = "Sort by field", example = "title")
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Sort direction", example = "ASC")
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        try {
            EPostSortBy ePostSortBy = EPostSortBy.valueOf(sortBy.toUpperCase(Locale.ENGLISH));

            return new ResponseEntity<>(
                    postService.getAllPosts(pageNo, pageSize, ePostSortBy, sortDir),
                    HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException(sortBy);
        }
    }

    @Operation(summary = "Get post by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the post",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found (Post not found)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessBaseResponse<PostResponse>> getPostById(
            @Parameter(description = "ID of the post")
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "Update a post", responses = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request (Validation Error)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not authorized)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found (Post not found)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessBaseResponse<PostResponse>> updatePost(
            @Valid @RequestBody PostPayload postPayload,
            @Parameter(description = "ID of the post")
            @PathVariable(name = "id") String id
    ) {
        return new ResponseEntity<>(
                postService.updatePost(postPayload, id),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete a post", responses = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Not authenticated)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not authorized)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not Found (Post not found)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessBaseResponse<String>> deletePost(
            @Parameter(description = "ID of the post")
            @PathVariable(name = "id") String id
    ) {
        return new ResponseEntity<>(
                postService.deletePostById(id),
                HttpStatus.OK
        );
    }
}
