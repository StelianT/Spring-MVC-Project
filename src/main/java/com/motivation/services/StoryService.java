package com.motivation.services;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddStoryBindingModel;
import com.motivation.models.viewModels.StoryViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoryService {

    void save(AddStoryBindingModel addStoryBindingModel);

    List<StoryViewModel> findAllStoriesByUserId(Long userId);

    Page<StoryViewModel> findAllStories(Pageable pageable);

    List<StoryViewModel> findAllStories();

    void like(User user, long storyId);

    void unlike(User user, long storyId);

    AddStoryBindingModel getOneById(long storyId);

    void editStory(AddStoryBindingModel addQuoteBindingModel, long storyId);
}
