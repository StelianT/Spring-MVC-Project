package com.motivation.controllers;

import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.services.QuoteService;
import com.motivation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    private final QuoteService quoteService;
    private final UserService userService;

    @Autowired
    public UserHomeController(QuoteService quoteService, UserService userService) {
        this.quoteService = quoteService;
        this.userService = userService;
    }

    @GetMapping("")
    public String getCurrentUserPage(Model model, Principal principal){
        String username = principal.getName();
        Long userId = this.userService.getUserIdByUsername(username);
        String fullName = this.userService.getFullNameByUsername(username);

        List<QuoteViewModel> quotes = this.quoteService.findAllQuotesByUserId(userId);
        model.addAttribute("quotes", quotes);
        model.addAttribute("fullName", fullName);
        model.addAttribute("username", username);
        return "user";
    }

    @GetMapping("/{userId}")
    public String getUserPageByUserId(@PathVariable long userId, Model model) {
        String username = this.userService.getUsernameByUserId(userId);
        String fullName = this.userService.getFullNameByUserId(userId);

        List<QuoteViewModel> quotes = this.quoteService.findAllQuotesByUserId(userId);
        model.addAttribute("quotes", quotes);
        model.addAttribute("fullName", fullName);
        model.addAttribute("username", username);

        return "user";
    }
}
