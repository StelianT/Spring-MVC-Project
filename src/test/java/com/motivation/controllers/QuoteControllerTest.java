package com.motivation.controllers;

import com.motivation.entities.BasicUser;
import com.motivation.entities.Quote;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.services.QuoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(QuoteController.class)
@ActiveProfiles("test")
public class QuoteControllerTest {

    public static final long QUOTE_ID = 1;

    public static final long USER_ID = 1;

    public static final long QUOTES_COUNT = 1;

    public static final String CONTENT = "Test Content";

    public static final String AUTHOR = "Test Author";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuoteService quoteService;

    @Before
    public void setUp() throws Exception {
        //Arrange
        QuoteViewModel quote = new QuoteViewModel();
        quote.setId(QUOTE_ID);
        quote.setContent(CONTENT);
        quote.setAuthor(AUTHOR);

        List<QuoteViewModel> quotes = new ArrayList<>();
        quotes.add(quote);

        when(this.quoteService.findAllQuotes()).thenReturn(quotes);
    }

    @Test
    public void getQuoteHomePage() throws Exception {
        //Act
        this.mvc
                .perform(get("/quotes"))
                .andExpect(status().isOk())
                .andExpect(view().name("quotes"))
                .andExpect(model().attribute("quotes", arrayWithSize(1)));

        verify(this.quoteService, times(1)).findAllQuotes();
        verifyNoMoreInteractions(this.quoteService);
    }

}