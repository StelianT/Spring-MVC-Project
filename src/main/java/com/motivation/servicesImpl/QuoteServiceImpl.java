package com.motivation.servicesImpl;

import com.motivation.entities.Quote;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.repository.QuoteRepository;
import com.motivation.repository.UserRepository;
import com.motivation.services.QuoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public QuoteServiceImpl(QuoteRepository quoteRepository, ModelMapper modelMapper) {
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(AddQuoteBindingModel addQuoteBindingModel) {
        Quote quote = this.modelMapper.map(addQuoteBindingModel, Quote.class);

        this.quoteRepository.save(quote);
    }

    @Override
    public List<QuoteViewModel> findAllQuotes() {
        List<QuoteViewModel> models = new ArrayList<>();
        List<Quote> quotes = this.quoteRepository.findAll();
        for (Quote quote : quotes) {
            QuoteViewModel model = this.modelMapper.map(quote, QuoteViewModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public List<QuoteViewModel> findAllQuotesByUserId(Long userId) {
        List<QuoteViewModel> models = new ArrayList<>();
        List<Quote> quotes = this.quoteRepository.findAllByAddedById(userId);
        for (Quote quote : quotes) {
            QuoteViewModel model = this.modelMapper.map(quote, QuoteViewModel.class);
            models.add(model);
        }

        return models;
    }
}
