package org.example.springblog.controller;

import org.example.springblog.model.Blog;
import org.example.springblog.model.Category;
import org.example.springblog.service.IBlogService;
import org.example.springblog.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> listCategory(){
        return categoryService.findAll();
    }

    @GetMapping
    public ModelAndView listBlog(@PageableDefault(size = 5) Pageable pageable){
        Page<Blog> blogs = blogService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/blog/index");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createNewBlog(){
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(Blog blog, RedirectAttributes redirectAttributes){
        blogService.save(blog);
        redirectAttributes.addFlashAttribute("success", "create new Blog successfully!");
        return "redirect:/blogs";
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id){
        Optional<Blog> blogOptional = blogService.findById(id);
        if (blogOptional.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/blog/update");
            modelAndView.addObject("blogs", blogOptional.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
        public String update(@ModelAttribute("blog") Blog blog, RedirectAttributes redirectAttributes){
        blogService.save(blog);
        redirectAttributes.addFlashAttribute("success", "update blog successfully!");
        return "redirect:/blogs";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        blogService.remove(id);
        redirectAttributes.addFlashAttribute("success", "delete blog successfully!");
        return "redirect:/blogs";
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Blog> blogs;
        if (search.isPresent()){
            blogs = blogService.findAllByTitleContaining(pageable, search.get());
        } else {
            blogs = blogService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/blog/index");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/category/{categoryId}")
    public ModelAndView listBlogByCategory(@PathVariable Long categoryId, @PageableDefault(size = 5) Pageable pageable){
        Optional<Category> categoryOptional = categoryService.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Iterable<Blog> blogs = blogService.findAllByCategory(category);
            ModelAndView modelAndView = new ModelAndView("/blog/index");
            modelAndView.addObject("blogs", blogs);
            modelAndView.addObject("category", category);
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

}
