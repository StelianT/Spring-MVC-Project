package com.motivation.repository;

import com.motivation.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "SELECT q FROM Quote AS q")
    List<Quote> findAll();

    List<Quote> findAllByAddedById(Long userId);

    Quote findOneById(Long quoteId);
}
