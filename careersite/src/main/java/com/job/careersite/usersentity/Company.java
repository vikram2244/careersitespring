package com.job.careersite.usersentity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String logo;
    private String website;
    
    @Transient
    private Long jobCount;
    
    @Transient
    private List<Job> jobs;
    
  
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public Long getJobCount() { return jobCount; }
    public void setJobCount(Long jobCount) { this.jobCount = jobCount; }
    public List<Job> getJobs() { return jobs; }
    public void setJobs(List<Job> jobs) { this.jobs = jobs; }
}