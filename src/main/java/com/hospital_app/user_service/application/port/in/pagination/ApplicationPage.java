package com.hospital_app.user_service.application.port.in.pagination;

import java.util.List;

public class ApplicationPage<T> {

    int getPageNumber;
    int getPageSize;
    int getTotalPages;
    long getTotalElements;
    boolean isLast;
    boolean isFirst;
    List<T> content;

    public ApplicationPage(int getPageNumber, int getPageSize, int getTotalPages, long getTotalElements, boolean isLast, boolean isFirst, List<T> content) {
        this.getPageNumber = getPageNumber;
        this.getPageSize = getPageSize;
        this.getTotalPages = getTotalPages;
        this.getTotalElements = getTotalElements;
        this.isLast = isLast;
        this.isFirst = isFirst;
        this.content = content;
    }

    public int getGetPageNumber() {
        return getPageNumber;
    }

    public int getGetPageSize() {
        return getPageSize;
    }

    public int getGetTotalPages() {
        return getTotalPages;
    }

    public long getGetTotalElements() {
        return getTotalElements;
    }

    public boolean isLast() {
        return isLast;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public List<T> getContent() {
        return content;
    }
}
