package com.motivation.repository;

import com.motivation.entities.LifeGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeGoalRepository extends JpaRepository<LifeGoal, Long> {

    List<LifeGoal> findAllByUserId(long userId);
}
