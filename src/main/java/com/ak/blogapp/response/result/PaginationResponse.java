package com.ak.blogapp.response.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "PaginationResponse", description = "Pagination Response Structure")
public class PaginationResponse<T> {
    @Schema(description = "Current page number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer page;

    @Schema(description = "Number of items per page", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageSize;

    @Schema(description = "Total number of pages", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalPages;

    @Schema(description = "Flag indicating whether the current page is the last page", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isLastPage;

    @Schema(description = "List of data items", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<T> data;

    public PaginationResponse(Integer page, Integer pageSize, Integer totalPages, List<T> data) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.isLastPage = (page + 1) >= totalPages;
        this.data = data;
    }
}
