package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Image;

import java.util.List;

public interface ImagesRepository extends CrudRepository<Image> {
    List<Image> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
