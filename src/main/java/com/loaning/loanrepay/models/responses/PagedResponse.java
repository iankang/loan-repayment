package com.loaning.loanrepay.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagedResponse<T> {

    private List<T> data;
    private int currentPage;
    private long totalItems;
    private int totalPages;


}
