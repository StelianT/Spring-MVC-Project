package com.motivation.servicesImpl;

import com.motivation.entities.Story;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddStoryBindingModel;
import com.motivation.models.viewModels.StoryViewModel;
import com.motivation.repository.StoryRepository;
import com.motivation.services.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository, ModelMapper modelMapper) {
        this.storyRepository = storyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(AddStoryBindingModel addStoryBindingModel) {
        Story story = new Story();
        story.setPerson(addStoryBindingModel.getPerson());
        story.setContent(addStoryBindingModel.getContent());
        story.setAddedBy(addStoryBindingModel.getAddedBy());
        Set<User> likedBy = new HashSet<>();
        story.setLikedBy(likedBy);

        this.storyRepository.save(story);
    }

    @Override
    public List<StoryViewModel> findAllStoriesByUserId(Long userId) {
        List<StoryViewModel> models = new ArrayList<>();
        List<Story> stories = this.storyRepository.findAllByAddedById(userId);
        for (Story story : stories) {
            StoryViewModel model = this.modelMapper.map(story, StoryViewModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public Page<StoryViewModel> findAllStories(Pageable pageable) {
        Page<Story> stories = this.storyRepository.findAll(pageable);
        List<StoryViewModel> storyViewModels = new ArrayList<>();
        for (Story story : stories) {
            StoryViewModel storyViewModel = this.modelMapper.map(story, StoryViewModel.class);
            storyViewModels.add(storyViewModel);
        }

        Page<StoryViewModel> storyModels = new PageImpl<>(storyViewModels, pageable,
                stories.getTotalElements());
        return storyModels;
    }

    @Override
    public List<StoryViewModel> findAllStories() {
        List<StoryViewModel> models = new ArrayList<>();
        List<Story> stories = this.storyRepository.findAll();
        for (Story story : stories) {
            StoryViewModel model = this.modelMapper.map(story, StoryViewModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public void like(User user, long storyId) {
        Story story = this.storyRepository.findOneById(storyId);
        story.getLikedBy().add(user);
        this.storyRepository.saveAndFlush(story);
    }

    @Override
    public void unlike(User user, long storyId) {
        Set<User> usersLikedStory = this.storyRepository.findOneById(storyId).getLikedBy();
        for (User userInCollection : usersLikedStory) {
            if (userInCollection.getUsername().equals(user.getUsername())) {
                usersLikedStory.remove(userInCollection);
                this.storyRepository.saveAndFlush(this.storyRepository.findOneById(storyId));
                return;
            }
        }
    }

    @Override
    public AddStoryBindingModel getOneById(long storyId) {
        Story story =  this.storyRepository.getOne(storyId);
        return this.modelMapper.map(story, AddStoryBindingModel.class);
    }

    @Override
    public void editStory(AddStoryBindingModel addQuoteBindingModel, long storyId) {
        Story story = this.storyRepository.getOne(storyId);
        story.setPerson(addQuoteBindingModel.getPerson());
        story.setContent(addQuoteBindingModel.getContent());
        this.storyRepository.save(story);
    }
}
