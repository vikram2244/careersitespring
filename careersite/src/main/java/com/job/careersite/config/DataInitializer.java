package com.job.careersite.config;

import com.job.careersite.usersentity.Category;
import com.job.careersite.usersentity.Company;
import com.job.careersite.usersentity.Job;
import com.job.careersite.usersrepo.CategoryRepository;
import com.job.careersite.usersrepo.CompanyRepository;
import com.job.careersite.usersrepo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize categories
        if (categoryRepository.count() == 0) {
            System.out.println("Initializing default categories...");
            Category[] categories = {
                new Category("Technology", "Code2"),
                new Category("Data Science", "BarChart3"),
                new Category("Design", "Palette"),
                new Category("Marketing", "Megaphone"),
                new Category("Sales", "TrendingUp"),
                new Category("Customer Support", "Headphones"),
                new Category("Finance", "Wallet"),
                new Category("Operations", "Package")
            };
            categoryRepository.saveAll(Arrays.asList(categories));
            System.out.println("Categories initialized!");
        }
        
        // Initialize companies
        if (companyRepository.count() == 0) {
            System.out.println("Initializing default companies...");
            
            Company[] companies = {
                createCompany("Tech Corp India", "Leading IT services company providing innovative solutions", 
                    "https://via.placeholder.com/100", "https://techcorp.com"),
                createCompany("DataWise Solutions", "Analytics and AI solutions provider", 
                    "https://via.placeholder.com/100", "https://datawise.com"),
                createCompany("Creative Pixels", "Digital design agency specializing in UI/UX", 
                    "https://via.placeholder.com/100", "https://creativepixels.com"),
                createCompany("Growth Hackers", "Digital marketing agency for startups", 
                    "https://via.placeholder.com/100", "https://growthhackers.com"),
                createCompany("ServerStack", "Cloud solutions and DevOps services", 
                    "https://via.placeholder.com/100", "https://serverstack.com")
            };
            
            companyRepository.saveAll(Arrays.asList(companies));
            System.out.println("Companies initialized!");
        }
        
        // Initialize sample jobs
        if (jobRepository.count() == 0) {
            System.out.println("Initializing sample jobs...");
            
            Job job1 = new Job();
            job1.setTitle("Frontend Developer");
            job1.setCompany("Tech Corp India");
            job1.setLocation("Bangalore");
            job1.setSalary("₹3-5 LPA");
            job1.setExperience("0-1 years");
            job1.setJobType("Full-time");
            job1.setCategory("Technology");
            job1.setDescription("We are looking for a passionate Frontend Developer to join our team.");
            job1.setEligibility("B.Tech in CS/IT, 2023/2024 graduates");
            job1.setSkills(Arrays.asList("React", "JavaScript", "CSS"));
            job1.setResponsibilities(Arrays.asList("Build responsive UIs", "Optimize performance"));
            job1.setApplyLink("https://example.com/apply/1");
            job1.setFeatured(true);
            job1.setTrending(true);
            jobRepository.save(job1);
            
            Job job2 = new Job();
            job2.setTitle("Data Analyst");
            job2.setCompany("DataWise Solutions");
            job2.setLocation("Mumbai");
            job2.setSalary("₹4-6 LPA");
            job2.setExperience("0-2 years");
            job2.setJobType("Full-time");
            job2.setCategory("Data Science");
            job2.setDescription("Join our data team to analyze business metrics");
            job2.setEligibility("B.Tech/MBA with analytical skills");
            job2.setSkills(Arrays.asList("Python", "SQL", "Tableau"));
            job2.setResponsibilities(Arrays.asList("Analyze data", "Create reports"));
            job2.setApplyLink("https://example.com/apply/2");
            job2.setFeatured(true);
            job2.setTrending(false);
            jobRepository.save(job2);
            
            Job job3 = new Job();
            job3.setTitle("UI/UX Designer");
            job3.setCompany("Creative Pixels");
            job3.setLocation("Remote");
            job3.setSalary("₹3-4 LPA");
            job3.setExperience("0-1 years");
            job3.setJobType("Internship");
            job3.setCategory("Design");
            job3.setDescription("Looking for creative UI/UX designers");
            job3.setEligibility("Design degree or portfolio");
            job3.setSkills(Arrays.asList("Figma", "Adobe XD"));
            job3.setResponsibilities(Arrays.asList("Create wireframes", "Design interfaces"));
            job3.setApplyLink("https://example.com/apply/3");
            job3.setFeatured(false);
            job3.setTrending(true);
            jobRepository.save(job3);
            
            Job job4 = new Job();
            job4.setTitle("Marketing Associate");
            job4.setCompany("Growth Hackers");
            job4.setLocation("Delhi NCR");
            job4.setSalary("₹2.5-3.5 LPA");
            job4.setExperience("0-1 years");
            job4.setJobType("Full-time");
            job4.setCategory("Marketing");
            job4.setDescription("Join our marketing team to execute digital campaigns");
            job4.setEligibility("MBA Marketing or BBA");
            job4.setSkills(Arrays.asList("Social Media", "Content Writing", "SEO"));
            job4.setResponsibilities(Arrays.asList("Run social media campaigns", "Create content"));
            job4.setApplyLink("https://example.com/apply/4");
            job4.setFeatured(false);
            job4.setTrending(false);
            jobRepository.save(job4);
            
            System.out.println("Sample jobs initialized!");
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