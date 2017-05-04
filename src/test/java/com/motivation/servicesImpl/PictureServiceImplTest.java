package com.motivation.servicesImpl;

import com.motivation.entities.BasicUser;
import com.motivation.entities.Picture;
import com.motivation.entities.Quote;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddPictureBindingModel;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.repository.PictureRepository;
import com.motivation.repository.QuoteRepository;
import com.motivation.services.PictureService;
import com.motivation.services.QuoteService;
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
public class PictureServiceImplTest {

    public static final long PICTURE_ID = 1;

    public static final long USER_ID = 1;

    public static final long PICTURES_COUNT = 1;

    public static final String TITLE = "Picture Title";

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private PictureRepository pictureRepository;

    @Autowired
    private PictureService pictureService;

    @Before
    public void setUp() throws Exception {
        Picture picture = new Picture();
        picture.setId(PICTURE_ID);
        picture.setTitle(TITLE);

        User user = new BasicUser();
        user.setId(USER_ID);

        picture.setAddedBy(user);
        when(this.pictureRepository.getOne(PICTURE_ID)).thenReturn(picture);
        List<Picture> pictures = new ArrayList<>();
        pictures.add(picture);
        when(this.pictureRepository.findAll()).thenReturn(pictures);
        when(this.pictureRepository.findAllByAddedById(USER_ID)).thenReturn(pictures);
    }

    @Test
    public void findAllPictures() throws Exception {
        //Act
        List<PictureViewModel> pictures = this.pictureService.findAllPictures();

        //Assert Count
        assertEquals(PICTURES_COUNT, pictures.size());
    }

    @Test
    public void getOneById() throws Exception {
        //Act
        AddPictureBindingModel picture = this.pictureService.getOneById(PICTURE_ID);

        //Assert
        assertEquals(TITLE, picture.getTitle());
        assertEquals(USER_ID, picture.getAddedBy().getId());
    }

    @Test
    public void findAllPicturesByUserId() throws Exception {
        //Act
        List<PictureViewModel> pictures = this.pictureService.findAllPicturesByUserId(PICTURE_ID);

        //Assert
        assertEquals(PICTURES_COUNT, pictures.size());
    }

}