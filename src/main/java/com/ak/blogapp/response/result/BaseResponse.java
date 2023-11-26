package com.ak.blogapp.response.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "BaseResponse", description = "Base Response Structure")
public class BaseResponse<T> {
    @Schema(description = "Flag indicating whether the operation is successful", requiredMode = Schema.RequiredMode.REQUIRED)
    private final boolean success;

    @JsonCreator
    public BaseResponse(
            @JsonProperty("success") boolean success
    ) {
        this.success = success;
    }
}