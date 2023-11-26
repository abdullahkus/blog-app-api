package com.ak.blogapp.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(name = "SuccessBaseResponse", description = "Success Base Response Structure")
public class SuccessBaseResponse<T> extends BaseResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public SuccessBaseResponse(T payload) {
        super(true);
        this.data = payload;
    }

    public static <T> SuccessBaseResponse<T> success(T payload) {
        return new SuccessBaseResponse<>(payload);
    }

    public static <T> SuccessBaseResponse<PaginationResponse<T>> successWithPagination(Integer page, Integer pageSize, Integer totalPages, List<T> listPayload) {
        PaginationResponse<T> payload = new PaginationResponse<>(page, pageSize, totalPages, listPayload);
        return new SuccessBaseResponse<>(payload);
    }
}