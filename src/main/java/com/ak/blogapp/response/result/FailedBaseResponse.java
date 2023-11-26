package com.ak.blogapp.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "FailedBaseResponse", description = "Failed Base Response Structure")
public class FailedBaseResponse extends BaseResponse<List<ErrorDetail>> {
    @JsonProperty("errors")
    private final List<ErrorDetail> errors;

    public FailedBaseResponse(List<ErrorDetail> errors) {
        super(false);
        this.errors = errors;
    }

    public static FailedBaseResponse fail(List<ErrorDetail> errors) {
        return new FailedBaseResponse(errors);
    }
}

