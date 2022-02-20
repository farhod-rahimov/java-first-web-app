package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UsersServiceException;
import edu.school21.cinema.services.UsersServiceExceptionEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6));
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new UserMapper(), new Object[]{id})
                .stream().findAny().orElse(null);

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new UserMapper(), new Object[]{email})
                .stream().findAny().orElse(null);

        return Optional.ofNullable(user);
    }

    @Override
    public void save(User entity) {
        int ret = jdbcTemplate.update("INSERT INTO users (firstname, lastname, email, phonenumber, password) VALUES (?, ?, ?, ?, ?)",
                entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhoneNumber(), entity.getPassword());

        if (ret == 0) {
            throw new UsersServiceException(UsersServiceExceptionEnum.USER_SAVE_ERROR);
        }
    }

    @Override
    public void update(User entity) {
        // implementation is not required yet
    }

    @Override
    public void deleteById(Long id) {
        // implementation is not required yet
    }

    @Override
    public void deleteByEmail(String email) {
        // implementation is not required yet
    }
}
