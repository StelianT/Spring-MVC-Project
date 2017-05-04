package com.motivation.repository;

import com.motivation.entities.BasicUser;
import com.motivation.entities.Quote;
import com.motivation.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class QuoteRepositoryTest {

    public static final String CONTENT = "Test Content";
    public static final String AUTHOR = "Test Author";
    public static final Long USER_ID = 1L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private QuoteRepository quoteRepository;

    @Before
    public void setUp() throws Exception {
        //Arrange
        Quote quote = new Quote();
        User user = new BasicUser();
        quote.setContent(CONTENT);
        quote.setAuthor(AUTHOR);
        quote.setColor("a");
        user.setId(USER_ID);
        quote.setAddedBy(user);
        quote.setLikedBy(new HashSet<>());
        this.em.persist(quote);
    }

    @Test
    public void findAllByAddedById() throws Exception {
        //Act
        List<Quote> quotes = this.quoteRepository.findAllByAddedById(USER_ID);

        //Assert
        assertEquals(1, quotes.size());

        //Assert
        Quote quote = quotes.get(0);
        Long quoteAddedByUserId = quote.getAddedBy().getId();
        assertEquals(CONTENT, quote.getContent());
        assertEquals(AUTHOR, quote.getAuthor());
        assertEquals(USER_ID, quoteAddedByUserId);
    }

}