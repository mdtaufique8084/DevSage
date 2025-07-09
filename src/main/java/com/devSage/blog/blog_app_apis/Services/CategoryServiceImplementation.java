package com.devSage.blog.blog_app_apis.Services;

import com.devSage.blog.blog_app_apis.Entity.Category;
import com.devSage.blog.blog_app_apis.Exceptions.ResourceNotFoundException;
import com.devSage.blog.blog_app_apis.Payloads.CategoryDto;
import com.devSage.blog.blog_app_apis.Repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImplementation implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=modelMapper.map(categoryDto, Category.class);
        Category addedCategory=categoryRepository.save(category);
        return modelMapper.map(addedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategories=categoryRepository.findAll();
        List<CategoryDto> categoryDtoList=allCategories.stream().
                map((catagory -> modelMapper.map(catagory,CategoryDto.class))).toList();
        return categoryDtoList;
    }
}
