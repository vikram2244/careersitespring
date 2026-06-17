package com.job.careersite.usersrepo;

import com.job.careersite.usersentity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    
    @Query("SELECT c, COUNT(j) FROM Company c LEFT JOIN Job j ON j.company = c.name GROUP BY c.id")
    List<Object[]> findAllWithJobCount();
}