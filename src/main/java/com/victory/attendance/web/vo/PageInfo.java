package com.victory.attendance.web.vo;

import java.util.List;

public class PageInfo {

    private Long totalElements;
    private List content;

    public PageInfo() {
    }

    public PageInfo(Long totalElements, List content) {
        this.totalElements = totalElements;
        this.content = content;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List getContent() {
        return content;
    }

    public void setContent(List content) {
        this.content = content;
    }
}
