package com.motivation.repository;

import com.motivation.entities.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query(value = "SELECT q FROM Quote AS q")
    List<Story> findAll();

    List<Story> findAllByAddedById(Long userId);

    Story findOneById(Long storyId);
}
