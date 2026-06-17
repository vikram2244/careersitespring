package com.job.careersite.usersentity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String icon;
    
    @Transient
    private Long jobCount;
public Category() {}
    
    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Long getJobCount() { return jobCount; }
    public void setJobCount(Long jobCount) { this.jobCount = jobCount; }
}