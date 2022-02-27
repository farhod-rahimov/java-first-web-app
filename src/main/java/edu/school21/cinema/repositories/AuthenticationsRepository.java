package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Authentication;

import java.util.List;

public interface AuthenticationsRepository extends CrudRepository<Authentication> {
    List<Authentication> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
