package com.motivation.services;

import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;

import java.util.List;

public interface QuoteService {

    void save(AddQuoteBindingModel addQuoteBindingModel);

    List<QuoteViewModel> findAllQuotes();
}
