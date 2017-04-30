package com.motivation.controllers;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddStoryBindingModel;
import com.motivation.models.viewModels.StoryViewModel;
import com.motivation.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("")
    public String getStoryHomePage(Model model, @PageableDefault(size = 6) Pageable pageable) {

        Page<StoryViewModel> stories = this.storyService.findAllStories(pageable);
        User currentUser = getCurrentUser();

        for (StoryViewModel story : stories) {
            Set<User> likedBy = story.getLikedBy();
            boolean isLikedByCurrentUser = false;
            for (User user : likedBy) {
                if (user.getUsername().equals(currentUser.getUsername())) {
                    isLikedByCurrentUser = true;
                }
            }
            story.setLikedByCurrentUser(isLikedByCurrentUser);
        }
        model.addAttribute("stories", stories);

        return "stories";
    }

    @GetMapping("/add")
    public String getAddQuotePage(@ModelAttribute AddStoryBindingModel addStoryBindingModel) {
        return "stories-add";
    }

    @PostMapping("/add")
    public String addQuote(@ModelAttribute AddStoryBindingModel addStoryBindingModel) {

        addStoryBindingModel.setAddedBy(getCurrentUser());

        this.storyService.save(addStoryBindingModel);

        return "redirect:/stories";
    }

    @PostMapping("/like/{storyId}")
    public ResponseEntity likeQuote(@PathVariable long storyId) {
        this.storyService.like(getCurrentUser(), storyId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/unlike/{storyId}")
    public ResponseEntity unlikeQuote(@PathVariable long storyId) {
        this.storyService.unlike(getCurrentUser(), storyId);

        return new ResponseEntity(HttpStatus.OK);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object myUser = (auth != null) ? auth.getPrincipal() :  null;

        return (User) myUser;
    }
}
