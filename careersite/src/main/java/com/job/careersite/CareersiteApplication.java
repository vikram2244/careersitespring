package com.job.careersite;
import com.job.careersite.servicejob.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
	    SecurityAutoConfiguration.class
	})
public class CareersiteApplication implements CommandLineRunner {
    
    @Autowired
    private CategoryService categoryService;
    
    public static void main(String[] args) {
        SpringApplication.run(CareersiteApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize default categories on startup
        categoryService.initializeDefaultCategories();
    }
}