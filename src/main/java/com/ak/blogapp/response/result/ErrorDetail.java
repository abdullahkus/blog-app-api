package com.ak.blogapp.response.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Schema(name = "ErrorDetail", description = "Error Detail Structure")
public class ErrorDetail {
    @Schema(description = "Timestamp of the error", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Date timestamp;

    @Schema(description = "Path where the error occurred", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String path;

    @Schema(description = "Error message", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String message;

    @Schema(description = "Error code", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
}