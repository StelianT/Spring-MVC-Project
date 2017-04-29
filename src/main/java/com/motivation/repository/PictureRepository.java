package com.motivation.repository;

import com.motivation.entities.Picture;
import com.motivation.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    @Query(value = "SELECT q FROM Picture AS q")
    List<Picture> findAll();

    Picture findOneById(Long pictureId);

    List<Picture> findAllByAddedById(Long userId);
}
