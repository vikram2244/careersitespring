package com.job.careersite.dto;

import com.job.careersite.usersentity.Job;
import java.util.List;

public class JobResponse {
    private List<Job> items;
    private long total;
    private int pages;
    private int page;
    
    public JobResponse() {}
    
    public JobResponse(List<Job> items, long total, int pages, int page) {
        this.items = items;
        this.total = total;
        this.pages = pages;
        this.page = page;
    }
    
  
    public List<Job> getItems() {
        return items;
    }
    
    public void setItems(List<Job> items) {
        this.items = items;
    }
    
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
}