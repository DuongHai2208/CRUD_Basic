package com.example.test.controller;

import com.example.test.DTO.Request.UserRequest;
import com.example.test.DTO.Request.UserUpdateRequest;
import com.example.test.entity.Client;
import com.example.test.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/crud")
public class controller {
    @Autowired
    private UserService userService;

    // Helper method to check if user is logged in
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    // Redirect to login if not authenticated
    private String redirectIfNotAuthenticated(HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/auth/login";
        }
        return null;
    }

    @PostMapping
    public String createUser(@ModelAttribute UserRequest request, Model model, HttpSession session) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) return redirect;

        userService.createUser(request);
        model.addAttribute("users", userService.getUsers());
        return "crud";
    }

    @GetMapping
    public String showCrudPage(Model model, HttpSession session) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) return redirect;

        List<Client> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("userRequest", new UserRequest());
        model.addAttribute("username", session.getAttribute("username"));
        return "crud";
    }

    @GetMapping("/create")
    public String getUsers(Model model, HttpSession session) {
        // Allow access to create page even if not logged in (for registration)
        List<Client> users = userService.getUsers();
        System.out.println("Users: " + users);
        model.addAttribute("users", users);
        model.addAttribute("userRequest", new UserRequest());

        // Add username to model if user is logged in
        if (session.getAttribute("username") != null) {
            model.addAttribute("username", session.getAttribute("username"));
        }

        return "create";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId, Model model, HttpSession session) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) return redirect;

        System.out.println("Deleting user with ID: " + userId);

        if (userId == null || userId.isEmpty()) {
            System.out.println("Error: User ID is null or empty");
            return "crud";
        }

        userService.deleteUser(userId);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("userRequest", new UserRequest());

        return "crud";
    }

    @GetMapping("/{userId}")
    public String getUser(@PathVariable String userId, Model model, HttpSession session) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) return redirect;

        Client user = userService.getUser(userId);
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("userRequest", new UserRequest());
        return "crud";
    }

    @PostMapping("/{userId}")
    public String updateUser(@PathVariable String userId, @ModelAttribute UserUpdateRequest request,
                             Model model, HttpSession session) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) return redirect;

        userService.updateUser(userId, request);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("userRequest", new UserRequest());
        return "crud";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model, HttpSession session) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) return redirect;

        List<Client> users = userService.searchUsers(keyword);
        model.addAttribute("users", users);
        model.addAttribute("userRequest", new UserRequest());
        return "crud";
    }
}