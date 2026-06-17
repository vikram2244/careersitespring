package com.job.careersite.servicejob;

import com.job.careersite.usersentity.Job;
import com.job.careersite.usersrepo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    // Get all jobs (without pagination) - for admin
    public List<Job> getAllJobsList() {
        return jobRepository.findAll();
    }
    
    // Get all jobs with filters and pagination
    public Page<Job> getAllJobs(String search, String category, String location, 
                                 Boolean featured, Boolean trending, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        
        if (featured != null && featured) {
            return jobRepository.findByFeaturedTrue(pageable);
        }
        if (trending != null && trending) {
            return jobRepository.findByTrendingTrue(pageable);
        }
        if (category != null && !category.isEmpty()) {
            return jobRepository.findByCategory(category, pageable);
        }
        if (location != null && !location.isEmpty()) {
            return jobRepository.findByLocationContaining(location, pageable);
        }
        if (search != null && !search.isEmpty()) {
            return jobRepository.findByTitleContainingOrCompanyContaining(search, search, pageable);
        }
        
        return jobRepository.findAll(pageable);
    }
    
    // Get job by ID
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
    
    // Get related jobs (same category, excluding current job)
    public List<Job> getRelatedJobs(String category, Long currentJobId) {
        Pageable pageable = PageRequest.of(0, 3);
        return jobRepository.findByCategoryAndIdNot(category, currentJobId, pageable);
    }
    
    // Create new job
    public Job createJob(Job job) {
        if (job.getCreatedAt() == null) {
            job.setCreatedAt(LocalDateTime.now());
        }
        return jobRepository.save(job);
    }
    
    // Update existing job
    public Job updateJob(Long id, Job jobDetails) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        
        existingJob.setTitle(jobDetails.getTitle());
        existingJob.setCompany(jobDetails.getCompany());
        existingJob.setLocation(jobDetails.getLocation());
        existingJob.setSalary(jobDetails.getSalary());
        existingJob.setExperience(jobDetails.getExperience());
        existingJob.setJobType(jobDetails.getJobType());
        existingJob.setCategory(jobDetails.getCategory());
        existingJob.setDescription(jobDetails.getDescription());
        existingJob.setEligibility(jobDetails.getEligibility());
        existingJob.setSkills(jobDetails.getSkills());
        existingJob.setResponsibilities(jobDetails.getResponsibilities());
        existingJob.setApplyLink(jobDetails.getApplyLink());
        existingJob.setLogo(jobDetails.getLogo());
        existingJob.setFeatured(jobDetails.getFeatured());
        existingJob.setTrending(jobDetails.getTrending());
        
        return jobRepository.save(existingJob);
    }
    
    // Delete job
    public void deleteJob(Long id) {
        Job job = getJobById(id);
        if (job != null) {
            jobRepository.delete(job);
        }
    }
    
    // Get paginated jobs
    public Page<Job> getPaginatedJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return jobRepository.findAll(pageable);
    }
    
    // Get featured jobs
    public List<Job> getFeaturedJobs() {
        Pageable pageable = PageRequest.of(0, 6);
        return jobRepository.findByFeaturedTrue(pageable).getContent();
    }
    
    // Get trending jobs
    public List<Job> getTrendingJobs() {
        Pageable pageable = PageRequest.of(0, 6);
        return jobRepository.findByTrendingTrue(pageable).getContent();
    }
    
    // Get latest jobs
    public List<Job> getLatestJobs() {
        return jobRepository.findTop6ByOrderByCreatedAtDesc();
    }
    
    // Get total job count
    public long getTotalJobCount() {
        return jobRepository.count();
    }
    
    // Count jobs by category
    public long countByCategory(String category) {
        return jobRepository.countByCategory(category);
    }
    
    // Count jobs by company
    public long countByCompany(String company) {
        return jobRepository.countByCompany(company);
    }
    
    // Get jobs by company
    public List<Job> getJobsByCompany(String company) {
        Pageable pageable = PageRequest.of(0, 20);
        return jobRepository.findByCompanyContaining(company, pageable).getContent();
    }
}