package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
