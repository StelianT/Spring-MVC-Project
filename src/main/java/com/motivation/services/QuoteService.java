package com.motivation.services;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.QuoteViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuoteService {

    void save(AddQuoteBindingModel addQuoteBindingModel);

    List<QuoteViewModel> findAllQuotes();

    List<QuoteViewModel> findAllQuotesByUserId(Long userId);

    Page<QuoteViewModel> findAllQuotes(Pageable pageable);

    void like(User user, long quoteId);

    void unlike(User user, long quoteId);
}
