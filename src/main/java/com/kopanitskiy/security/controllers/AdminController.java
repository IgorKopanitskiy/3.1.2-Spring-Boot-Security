package com.kopanitskiy.security.controllers;

import com.kopanitskiy.security.entities.Role;
import com.kopanitskiy.security.entities.User;
import com.kopanitskiy.security.services.RoleServiceImpl;
import com.kopanitskiy.security.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final RoleServiceImpl roleService;
    private final UserServiceImpl userService;


    @GetMapping("/users")
    public String showAll(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "adminView";
    }


    @GetMapping("/users/new")
    public String addNew(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "addNewView";
    }


    @PostMapping("/users")
    public String save(@ModelAttribute("user") User user,
                       @RequestParam(required = false) List<Long> roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin/users";
    }


    @GetMapping("/users/{id}/edit")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "addNewView";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(required = false) List<Long> roles) {

        userService.updateUser(id, user, roles);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}")
    public String deleteById(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

}

