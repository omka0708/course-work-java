package com.mirea.coursework.controllers;

import com.mirea.coursework.models.Post;
import com.mirea.coursework.repo.PostRepository;
import com.mirea.coursework.services.HeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MenuController {

    @Autowired
    private HeaderService headerService;

    @Autowired
    private PostRepository postRepository;


    @GetMapping("/menu")
    public String menuMain(Model model){
        model.addAttribute("title", "Меню");
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("hiddenEl", headerService.isUser());

        return "menu-main";
    }

    @PreAuthorize("hasAuthority('developers:write')")
    @GetMapping("/menu/add")
    public String menuAdd(Model model){
        model.addAttribute("title", "Добавление продуктов");
        model.addAttribute("hiddenEl", headerService.isUser());
        return "menu-add";
    }

    @PreAuthorize("hasAuthority('developers:write')")
    @PostMapping("/menu/add")
    public String menuPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/menu";
    }

    @GetMapping("/menu/{id}")
    public String menuDetails(Model model, @PathVariable(value = "id") long id){
        if(!postRepository.existsById(id)){
            return "redirect:/menu";
        }
        model.addAttribute("title", "Описание");
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        model.addAttribute("hiddenEl", headerService.isUser());
        return "menu-details";
    }

    @PreAuthorize("hasAuthority('developers:write')")
    @GetMapping("/menu/{id}/edit")
    public String menuEdit(Model model, @PathVariable(value = "id") long id){
        if(!postRepository.existsById(id)){
            return "redirect:/menu";
        }
        model.addAttribute("title", "Редактирование продукта");
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        model.addAttribute("hiddenEl", headerService.isUser());
        return "menu-edit";
    }

    @PreAuthorize("hasAuthority('developers:write')")
    @PostMapping("/menu/{id}/edit")
    public String menuPostUpdate(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model, @PathVariable(value = "id") long id){
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new AssertionError();
        });
        post.setTitle(title);
        post.setPrice(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/menu";
    }

    @PreAuthorize("hasAuthority('developers:write')")
    @PostMapping("/menu/{id}/remove")
    public String menuPostDelete(Model model, @PathVariable(value = "id") long id){
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new AssertionError();
        });
        postRepository.delete(post);
        return "redirect:/menu";
    }
}
