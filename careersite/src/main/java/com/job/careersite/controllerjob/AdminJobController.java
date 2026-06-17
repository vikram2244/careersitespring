package com.job.careersite.controllerjob;

import com.job.careersite.servicejob.JobService;
import com.job.careersite.usersentity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:5173", "https://shimmering-liger-862c4b.netlify.app/"})
public class AdminJobController {
    
    @Autowired
    private JobService jobService;
    
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobsList();
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(job);
    }
    
    @PostMapping("/jobs")
    public ResponseEntity<?> createJob(@RequestBody Map<String, Object> payload) {
        try {
            Job job = new Job();
            
            // Map fields from payload to Job entity
            job.setTitle((String) payload.get("title"));
            job.setCompany((String) payload.get("company"));
            job.setLocation((String) payload.get("location"));
            job.setSalary((String) payload.get("salary"));
            job.setExperience((String) payload.get("experience"));
            
            // Handle both job_type and jobType field names
            String jobType = (String) payload.get("jobType");
            if (jobType == null) {
                jobType = (String) payload.get("job_type");
            }
            job.setJobType(jobType);
            
            job.setCategory((String) payload.get("category"));
            job.setDescription((String) payload.get("description"));
            job.setEligibility((String) payload.get("eligibility"));
            
            // Handle skills (can be List or comma-separated string)
            Object skillsObj = payload.get("skills");
            if (skillsObj instanceof List) {
                job.setSkills((List<String>) skillsObj);
            } else if (skillsObj instanceof String) {
                job.setSkills(List.of(((String) skillsObj).split(",")));
            }
            
            // Handle responsibilities
            Object responsibilitiesObj = payload.get("responsibilities");
            if (responsibilitiesObj instanceof List) {
                job.setResponsibilities((List<String>) responsibilitiesObj);
            } else if (responsibilitiesObj instanceof String) {
                job.setResponsibilities(List.of(((String) responsibilitiesObj).split("\n")));
            }
            
            // Handle both apply_link and applyLink field names
            String applyLink = (String) payload.get("applyLink");
            if (applyLink == null) {
                applyLink = (String) payload.get("apply_link");
            }
            job.setApplyLink(applyLink);
            
            job.setLogo((String) payload.get("logo"));
            job.setFeatured((Boolean) payload.getOrDefault("featured", false));
            job.setTrending((Boolean) payload.getOrDefault("trending", false));
            
            // Validation
            if (job.getTitle() == null || job.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Title is required"));
            }
            if (job.getCompany() == null || job.getCompany().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Company is required"));
            }
            if (job.getLocation() == null || job.getLocation().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Location is required"));
            }
            if (job.getSalary() == null || job.getSalary().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Salary is required"));
            }
            if (job.getDescription() == null || job.getDescription().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Description is required"));
            }
            if (job.getApplyLink() == null || job.getApplyLink().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Apply link is required"));
            }
            
            Job createdJob = jobService.createJob(job);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error creating job: " + e.getMessage()));
        }
    }
    
    @PutMapping("/jobs/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody Job job) {
        try {
            Job updatedJob = jobService.updateJob(id, job);
            return ResponseEntity.ok(updatedJob);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error updating job: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.ok(Map.of("message", "Job deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Job not found with id: " + id));
        }
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total_jobs", jobService.getTotalJobCount());
        stats.put("featured_jobs", (long) jobService.getFeaturedJobs().size());
        stats.put("trending_jobs", (long) jobService.getTrendingJobs().size());
        stats.put("total_users", 0);
        stats.put("total_applications", 0);
        stats.put("revenue", 0);
        return ResponseEntity.ok(stats);
    }
}
