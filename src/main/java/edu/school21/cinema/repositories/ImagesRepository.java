package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Image;

import java.util.List;
import java.util.Optional;

public interface ImagesRepository extends CrudRepository<Image> {
    List<Image> findAllByUserId(Long userId);
    Optional<Image> findByUniqueNameAndUserId(String uniqueName, Long userId);
    void deleteAllByUserId(Long userId);
}
