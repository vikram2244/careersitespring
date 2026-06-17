package com.job.careersite.servicejob;

import com.job.careersite.usersentity.Category;
import com.job.careersite.usersrepo.CategoryRepository;
import com.job.careersite.usersrepo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        
        // Get job count for each category
        for (Category category : categories) {
            long jobCount = jobRepository.countByCategory(category.getName());
            category.setJobCount(jobCount);
        }
        
        return categories;
    }
    
    public List<Map<String, Object>> getAllCategoriesWithJobCount() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        
        for (Category category : categories) {
            Map<String, Object> catMap = new HashMap<>();
            catMap.put("id", category.getId());
            catMap.put("name", category.getName());
            catMap.put("icon", category.getIcon());
            catMap.put("job_count", jobRepository.countByCategory(category.getName()));
            result.add(catMap);
        }
        
        return result;
    }
    
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category already exists: " + category.getName());
        }
        return categoryRepository.save(category);
    }
    
    public void initializeDefaultCategories() {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Technology", "Code2"));
            categoryRepository.save(new Category("Data Science", "BarChart3"));
            categoryRepository.save(new Category("Design", "Palette"));
            categoryRepository.save(new Category("Marketing", "Megaphone"));
            categoryRepository.save(new Category("Sales", "TrendingUp"));
            categoryRepository.save(new Category("Customer Support", "Headphones"));
            categoryRepository.save(new Category("Finance", "Wallet"));
            categoryRepository.save(new Category("Operations", "Package"));
        }
    }
}