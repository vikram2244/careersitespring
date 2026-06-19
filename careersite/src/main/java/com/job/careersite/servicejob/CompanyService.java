package com.job.careersite.servicejob;

import com.job.careersite.usersentity.Company;
import com.job.careersite.usersentity.Job;
import com.job.careersite.usersrepo.CompanyRepository;
import com.job.careersite.usersrepo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    public List<Map<String, Object>> getAllCompanies() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Company> companies = companyRepository.findAll();
        
        for (Company company : companies) {
            Map<String, Object> companyMap = new HashMap<>();
            companyMap.put("id", company.getId());
            companyMap.put("name", company.getName());
            companyMap.put("description", company.getDescription());
            companyMap.put("logo", company.getLogo());
            companyMap.put("website", company.getWebsite());
            
          
            long jobCount = jobRepository.countByCompany(company.getName());
            companyMap.put("job_count", jobCount);
            
            result.add(companyMap);
        }
        
        return result;
    }
    
    public Map<String, Object> getCompanyById(Long id) {
        Optional<Company> companyOpt = companyRepository.findById(id);
        
        if (companyOpt.isEmpty()) {
            throw new IllegalArgumentException("Company not found with id: " + id);
        }
        
        Company company = companyOpt.get();
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", company.getId());
        result.put("name", company.getName());
        result.put("description", company.getDescription());
        result.put("logo", company.getLogo());
        result.put("website", company.getWebsite());
        
      
        Pageable pageable = PageRequest.of(0, 20);
        List<Job> jobs = jobRepository.findByCompanyContaining(company.getName(), pageable).getContent();
        result.put("jobs", jobs);
        result.put("job_count", (long) jobs.size());
        
        return result;
    }
    
    public Company createCompany(Company company) {
        if (companyRepository.findByName(company.getName()).isPresent()) {
            throw new IllegalArgumentException("Company already exists: " + company.getName());
        }
        return companyRepository.save(company);
    }
    
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + id));
        
        company.setName(companyDetails.getName());
        company.setDescription(companyDetails.getDescription());
        company.setLogo(companyDetails.getLogo());
        company.setWebsite(companyDetails.getWebsite());
        
        return companyRepository.save(company);
    }
    
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
    
    public void initializeDefaultCompanies() {
        if (companyRepository.count() == 0) {
            List<Company> defaultCompanies = Arrays.asList(
                createCompany("Tech Corp India", "Leading IT services company providing innovative solutions", 
                    "https://via.placeholder.com/100", "https://techcorp.com"),
                createCompany("DataWise Solutions", "Analytics and AI solutions provider", 
                    "https://via.placeholder.com/100", "https://datawise.com"),
                createCompany("Creative Pixels", "Digital design agency specializing in UI/UX", 
                    "https://via.placeholder.com/100", "https://creativepixels.com"),
                createCompany("Growth Hackers", "Digital marketing agency for startups", 
                    "https://via.placeholder.com/100", "https://growthhackers.com"),
                createCompany("ServerStack", "Cloud solutions and DevOps services", 
                    "https://via.placeholder.com/100", "https://serverstack.com"),
                createCompany("FinancePro", "Financial services and consulting", 
                    "https://via.placeholder.com/100", "https://financepro.com")
            );
            
            companyRepository.saveAll(defaultCompanies);
            System.out.println("Default companies initialized!");
        }
    }
    
    private Company createCompany(String name, String description, String logo, String website) {
        Company company = new Company();
        company.setName(name);
        company.setDescription(description);
        company.setLogo(logo);
        company.setWebsite(website);
        return company;
    }
}