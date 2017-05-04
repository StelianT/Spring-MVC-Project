package com.motivation.servicesImpl;

import com.motivation.entities.BasicUser;
import com.motivation.entities.Quote;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.repository.QuoteRepository;
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
public class QuoteServiceImplTest {

    public static final long QUOTE_ID = 1;

    public static final long USER_ID = 1;

    public static final long QUOTES_COUNT = 1;

    public static final String CONTENT = "Test Content";

    public static final String AUTHOR = "Test Author";

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteService quoteService;

    @Before
    public void setUp() throws Exception {
        Quote quote = new Quote();
        quote.setId(QUOTE_ID);
        quote.setContent(CONTENT);
        quote.setAuthor(AUTHOR);

        User user = new BasicUser();
        user.setId(USER_ID);

        quote.setAddedBy(user);
        when(this.quoteRepository.getOne(QUOTE_ID)).thenReturn(quote);
        List<Quote> quotes = new ArrayList<>();
        quotes.add(quote);
        when(this.quoteRepository.findAll()).thenReturn(quotes);
        when(this.quoteRepository.findAllByAddedById(USER_ID)).thenReturn(quotes);
    }

    @Test
    public void findAllQuotes() throws Exception {
        //Act
        List<QuoteViewModel> quotes = this.quoteService.findAllQuotes();

        //Assert Count
        assertEquals(QUOTES_COUNT, quotes.size());
    }

    @Test
    public void findAllQuotesByUserId() throws Exception {
        //Act
        List<QuoteViewModel> quotes = this.quoteService.findAllQuotesByUserId(USER_ID);

        //Assert
        assertEquals(QUOTES_COUNT, quotes.size());
    }

    @Test
    public void getOneById() throws Exception {
        //Act
        AddQuoteBindingModel quote = this.quoteService.getOneById(QUOTE_ID);

        //Assert
        assertEquals(CONTENT, quote.getContent());
        assertEquals(AUTHOR, quote.getAuthor());
        assertEquals(USER_ID, quote.getAddedBy().getId());
    }
}