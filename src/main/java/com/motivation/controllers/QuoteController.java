package com.motivation.controllers;

import com.motivation.entities.Quote;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.services.QuoteService;
import com.motivation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public String getQuoteHomePage(Model model, @PageableDefault(size = 6) Pageable pageable) {

        Page<QuoteViewModel> quotes = this.quoteService.findAllQuotes(pageable);
        User currentUser = getCurrentUser();

        for (QuoteViewModel quote : quotes) {
            Set<User> likedBy = quote.getLikedBy();
            boolean isLikedByCurrentUser = false;
            for (User user : likedBy) {
                if (user.getUsername().equals(currentUser.getUsername())) {
                    isLikedByCurrentUser = true;
                }
            }
            quote.setLikedByCurrentUser(isLikedByCurrentUser);
        }
        model.addAttribute("quotes", quotes);
        
        return "quotes";
    }

    @GetMapping("/add")
    public String getAddQuotePage(@ModelAttribute AddQuoteBindingModel addQuoteBindingModel) {
        return "quotes-add";
    }

    @PostMapping("/add")
    public String addQuote(@ModelAttribute AddQuoteBindingModel addQuoteBindingModel) {

        addQuoteBindingModel.setAddedBy(getCurrentUser());

        this.quoteService.save(addQuoteBindingModel);

        return "redirect:/quotes";
    }

    @PostMapping("/like/{quoteId}")
    public ResponseEntity likeQuote(@PathVariable long quoteId) {
        this.quoteService.like(getCurrentUser(), quoteId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/unlike/{quoteId}")
    public ResponseEntity unlikeQuote(@PathVariable long quoteId) {
        this.quoteService.unlike(getCurrentUser(), quoteId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/edit/{quoteId}")
    public String editQuote(@PathVariable long quoteId, Model model) {
        AddQuoteBindingModel quote = this.quoteService.getOneById(quoteId);
        model.addAttribute("addQuoteBindingModel", quote);

        return "quote-edit";
    }

    @PostMapping("/edit/{quoteId}")
    public String editQuote(@ModelAttribute AddQuoteBindingModel addQuoteBindingModel, @PathVariable long quoteId) {
        if (this.quoteService.getOneById(quoteId).getAddedBy().getId() != getCurrentUser().getId()) {
            return "error/access-denied";
        }

        this.quoteService.editQuote(addQuoteBindingModel, quoteId);

        return "redirect:/quotes";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object myUser = (auth != null) ? auth.getPrincipal() :  null;

        return (User) myUser;
    }
}
