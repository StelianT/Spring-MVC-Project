package com.motivation.controllers;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddLifeGoalBindingModel;
import com.motivation.models.viewModels.LifeGoalViewModel;
import com.motivation.services.LifeGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    private final LifeGoalService lifeGoalService;

    @Autowired
    public HomeController(LifeGoalService lifeGoalService) {
        this.lifeGoalService = lifeGoalService;
    }

    @GetMapping("/")
    public String getHomePageAuth(Model model, @AuthenticationPrincipal User activeUser,
                                  @ModelAttribute AddLifeGoalBindingModel addLifeGoalBindingModel) {
        if (activeUser != null) {
            List<LifeGoalViewModel> lifeGoals = this.lifeGoalService.findAllLifeGoalsByUserId(activeUser.getId());
            model.addAttribute("lifeGoals", lifeGoals);
        }
        return "index";
    }

    @PostMapping("/")
    public String addLifeGoal(@ModelAttribute AddLifeGoalBindingModel addLifeGoalBindingModel) {

        addLifeGoalBindingModel.setUser(getCurrentUser());

        this.lifeGoalService.save(addLifeGoalBindingModel);

        return "redirect:/";
    }

    @PostMapping("/completeLifeGoal/{lifeGoalId}")
    public String setLifeGoalAsCompleted(@PathVariable long lifeGoalId) {
        this.lifeGoalService.completeLifeGoal(lifeGoalId);

        return "redirect:/";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object myUser = (auth != null) ? auth.getPrincipal() :  null;

        return (User) myUser;
    }
}
