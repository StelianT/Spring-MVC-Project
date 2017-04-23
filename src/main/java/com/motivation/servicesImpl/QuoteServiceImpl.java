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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Page<QuoteViewModel> findAllQuotes(Pageable pageable) {
        Page<Quote> quotes = this.quoteRepository.findAll(pageable);
        List<QuoteViewModel> quoteViewModels = new ArrayList<>();
        for (Quote quote : quotes) {
            QuoteViewModel quoteViewModel = this.modelMapper.map(quote, QuoteViewModel.class);
            quoteViewModels.add(quoteViewModel);
        }

        Page<QuoteViewModel> quoteModels = new PageImpl<>(quoteViewModels, pageable,
                                                                        quotes.getTotalElements());
        return quoteModels;
    }

    @Override
    public void like(User user, long quoteId) {
        Quote quote = this.quoteRepository.findOneById(quoteId);
        quote.getLikedBy().add(user);
        this.quoteRepository.save(quote);
    }

    @Override
    public void unlike(User user, long quoteId) {
        Quote quote = this.quoteRepository.findOneById(quoteId);
        quote.getLikedBy().remove(user);
        this.quoteRepository.findOneById(quoteId).getLikedBy().clear();
        this.quoteRepository.save(quote);
    }
}
