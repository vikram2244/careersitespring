package com.job.careersite.controllerjob;

import com.job.careersite.dto.JobResponse;
import com.job.careersite.servicejob.JobService;
import com.job.careersite.usersentity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "https://shimmering-liger-862c4b.netlify.app"})
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @GetMapping("/jobs")
    public ResponseEntity<JobResponse> getJobs(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) Boolean trending,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit) {
        
        Page<Job> jobPage = jobService.getAllJobs(q, category, location, featured, trending, page, limit);
        
        JobResponse response = new JobResponse(
            jobPage.getContent(),
            jobPage.getTotalElements(),
            jobPage.getTotalPages(),
            page
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/jobs/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("job", job);
        response.put("related", jobService.getRelatedJobs(job.getCategory(), id));
        
        return ResponseEntity.ok(response);
    }
}
