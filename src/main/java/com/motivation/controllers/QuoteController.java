package com.motivation.controllers;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public String getQuoteHomePage(Model model) {

        List<QuoteViewModel> quotes = this.quoteService.findAllQuotes();
        model.addAttribute("quotes", quotes);
        return "quotes";
    }

    @GetMapping("/add")
    public String getAddQuotePage(@ModelAttribute AddQuoteBindingModel addQuoteBindingModel) {
        return "quotes-add";
    }

    @PostMapping("/add")
    public String addQuote(@ModelAttribute AddQuoteBindingModel addQuoteBindingModel) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        addQuoteBindingModel.setAddedBy((User) principal);

        this.quoteService.save(addQuoteBindingModel);

        return "redirect:/quotes";
    }
}
