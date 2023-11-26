package com.ak.blogapp.controller;

import com.ak.blogapp.payload.RolePayload;
import com.ak.blogapp.response.RoleResponse;
import com.ak.blogapp.response.result.ErrorDetail;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.service.RoleService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "RoleController", description = "Operations related to role")
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Create a new role", responses = {
            @ApiResponse(responseCode = "201", description = "Role created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleResponse.class))),
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
    public ResponseEntity<SuccessBaseResponse<RoleResponse>> createRole(
            @RequestBody @Valid @Parameter(description = "Role creation payload", required = true) RolePayload payload) {
        return new ResponseEntity<>(
                roleService.createRole(payload),
                HttpStatus.CREATED);
    }
}
