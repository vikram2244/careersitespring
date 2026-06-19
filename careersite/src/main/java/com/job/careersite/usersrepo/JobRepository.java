package com.job.careersite.usersrepo;

import com.job.careersite.usersentity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
   
    Page<Job> findByTitleContainingOrCompanyContaining(String title, String company, Pageable pageable);
    Page<Job> findByCategory(String category, Pageable pageable);
    Page<Job> findByLocationContaining(String location, Pageable pageable);
    Page<Job> findByFeaturedTrue(Pageable pageable);
    Page<Job> findByTrendingTrue(Pageable pageable);
    
   
    List<Job> findTop6ByOrderByCreatedAtDesc();
    
    
    @Query("SELECT j FROM Job j WHERE j.category = :category AND j.id != :jobId")
    List<Job> findByCategoryAndIdNot(@Param("category") String category, @Param("jobId") Long jobId, Pageable pageable);
    
   
    long countByCompany(String company);    
    long countByCategory(String category);
    
    
    Page<Job> findByCompanyContaining(String company, Pageable pageable);
    
   
    @Query("SELECT j.category, COUNT(j) FROM Job j GROUP BY j.category")
    List<Object[]> countJobsByCategory();
    
    @Query("SELECT j.company, COUNT(j) FROM Job j GROUP BY j.company")
    List<Object[]> countJobsByCompany();
}