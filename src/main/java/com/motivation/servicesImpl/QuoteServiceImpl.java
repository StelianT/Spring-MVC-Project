package com.motivation.servicesImpl;

import com.motivation.entities.Quote;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.repository.QuoteRepository;
import com.motivation.services.QuoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private ModelMapper modelMapper;

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
}
