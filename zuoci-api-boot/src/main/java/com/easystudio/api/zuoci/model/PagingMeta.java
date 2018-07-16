package com.easystudio.api.zuoci.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Page Info Meta")
@JsonPropertyOrder({"totalPages", "totalElements", "pageNumber", "pageSize"})
public class PagingMeta {

    @JsonProperty(value = "total-pages")
    @ApiModelProperty(value = "The total number of pages", required = true, readOnly = true, example = "5")
    private long totalPages;

    @JsonProperty(value = "total-elements")
    @ApiModelProperty(value = "The total number of resources", required = true, readOnly = true, example = "99")
    private long totalElements;

    @JsonProperty(value = "page-number")
    @ApiModelProperty(value = "The current page number", required = true, readOnly = true, example = "3")
    private long pageNumber;

    @JsonProperty(value = "page-size")
    @ApiModelProperty(value = "The current page size", required = true, readOnly = true, example = "20")
    private long pageSize;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
