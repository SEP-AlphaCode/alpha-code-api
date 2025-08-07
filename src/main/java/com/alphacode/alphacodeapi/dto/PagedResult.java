package com.alphacode.alphacodeapi.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedResult<T> {
    private List<T> data;
    private long total_count;
    private int page;
    private int per_page;
    private int total_pages;
    private boolean has_next;
    private boolean has_previous;

    public PagedResult(Page<T> pageData) {
        this.data = pageData.getContent();
        this.total_count = pageData.getTotalElements();
        this.page = pageData.getNumber() + 1;
        this.per_page = pageData.getSize();
        this.total_pages = pageData.getTotalPages();
        this.has_next = pageData.hasNext();
        this.has_previous = pageData.hasPrevious();
    }
}
