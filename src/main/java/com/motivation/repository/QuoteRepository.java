package com.motivation.repository;

import com.motivation.entities.Quote;
import com.motivation.models.viewModels.QuoteViewModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {

    @Query(value = "SELECT q FROM Quote AS q")
    List<Quote> findAll();

    List<Quote> findAllByAddedById(Long userId);
}
