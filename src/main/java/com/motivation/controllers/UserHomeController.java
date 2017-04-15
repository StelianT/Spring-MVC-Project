package com.motivation.controllers;

import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.services.QuoteService;
import com.motivation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserHomeController {

    private final QuoteService quoteService;
    private final UserService userService;

    @Autowired
    public UserHomeController(QuoteService quoteService, UserService userService) {
        this.quoteService = quoteService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal){
        String username = principal.getName();
        Long userId = this.userService.getUserIdByUsername(username);
        String fullName = this.userService.getFullNameByUsername(username);

        List<QuoteViewModel> quotes = this.quoteService.findAllQuotesByUserId(userId);
        model.addAttribute("quotes", quotes);
        model.addAttribute("fullName", fullName);
        return "user";
    }
}
