package org.example.springblog.controller;

import org.example.springblog.model.Blog;
import org.example.springblog.model.Category;
import org.example.springblog.service.IBlogService;
import org.example.springblog.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBlogService blogService;

    @GetMapping
    public ModelAndView listCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/index");
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createNewCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("categories", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes){
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("success", "add new category successfully!");
        return "redirect:/categories";
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/category/edit");
            modelAndView.addObject("categories", category.get());
            return modelAndView;
        } else
            return new ModelAndView("/error_404");
    }

    @PostMapping("/update/{id}")
    private String update(@ModelAttribute("categories") Category category, RedirectAttributes redirectAttributes){
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("success", "update category successfully");
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    private String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        categoryService.remove(id);
        redirectAttributes.addFlashAttribute("success", "delete category successfully!");
        return "redirect:/categories";
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView view(@PathVariable("id") Long id){
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()){
            return new ModelAndView("/error_404");
        }
        Iterable<Blog> blogs = blogService.findAllByCategory(category.get());
        ModelAndView modelAndView = new ModelAndView("/category/index");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }
}
