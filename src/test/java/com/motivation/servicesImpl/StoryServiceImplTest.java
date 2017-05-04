package com.motivation.servicesImpl;

import com.motivation.entities.BasicUser;
import com.motivation.entities.Story;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddStoryBindingModel;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.models.viewModels.StoryViewModel;
import com.motivation.repository.StoryRepository;
import com.motivation.services.StoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StoryServiceImplTest {

    public static final long STORY_ID = 1;

    public static final long USER_ID = 1;

    public static final long STORIES_COUNT = 1;

    public static final String CONTENT = "Test Content";

    public static final String PERSON = "Test Person";

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private StoryRepository storyRepository;

    @Autowired
    private StoryService storyService;

    @Before
    public void setUp() throws Exception {
        Story story = new Story();
        story.setId(STORY_ID);
        story.setContent(CONTENT);
        story.setPerson(PERSON);

        User user = new BasicUser();
        user.setId(USER_ID);

        story.setAddedBy(user);
        when(this.storyRepository.getOne(STORY_ID)).thenReturn(story);
        List<Story> stories = new ArrayList<>();
        stories.add(story);
        when(this.storyRepository.findAll()).thenReturn(stories);
        when(this.storyRepository.findAllByAddedById(USER_ID)).thenReturn(stories);
    }

    @Test
    public void findAllStoriesByUserId() throws Exception {
        //Act
        List<StoryViewModel> stories = this.storyService.findAllStoriesByUserId(USER_ID);

        //Assert
        assertEquals(STORIES_COUNT, stories.size());
    }

    @Test
    public void findAllStories() throws Exception {
        //Act
        List<StoryViewModel> stories = this.storyService.findAllStories();

        //Assert Count
        assertEquals(STORIES_COUNT, stories.size());
    }

    @Test
    public void getOneById() throws Exception {
        //Act
        AddStoryBindingModel story = this.storyService.getOneById(STORY_ID);

        //Assert
        assertEquals(CONTENT, story.getContent());
        assertEquals(PERSON, story.getPerson());
        assertEquals(USER_ID, story.getAddedBy().getId());
    }

}