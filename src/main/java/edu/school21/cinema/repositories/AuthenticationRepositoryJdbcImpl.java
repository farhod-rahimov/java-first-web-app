package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Authentication;
import edu.school21.cinema.services.UsersServiceException;
import edu.school21.cinema.services.UsersServiceExceptionEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthenticationRepositoryJdbcImpl implements AuthenticationRepository {

    private JdbcTemplate jdbcTemplate;

    public AuthenticationRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class AuthenticationMapper implements RowMapper<Authentication> {
        @Override
        public Authentication mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Authentication(resultSet.getLong(1),
                    resultSet.getDate(2),
                    resultSet.getTime(3),
                    resultSet.getString(4),
                    resultSet.getLong(5));
        }
    }

    @Override
    public List<Authentication> findAllByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM authentications WHERE userId = ?",
                        new AuthenticationRepositoryJdbcImpl.AuthenticationMapper(), new Object[]{userId});
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        // implementation is not required yet
    }

    @Override
    public Optional<Authentication> findById(Long id) {
        Authentication auth = jdbcTemplate.query("SELECT * FROM authentications WHERE authId = ?",
                        new AuthenticationRepositoryJdbcImpl.AuthenticationMapper(), new Object[]{id})
                .stream().findAny().orElse(null);

        return Optional.ofNullable(auth);
    }

    @Override
    public List<Authentication> findAll() {
        return jdbcTemplate.query("SELECT * FROM authentications", new AuthenticationRepositoryJdbcImpl.AuthenticationMapper());
    }

    @Override
    public void save(Authentication entity) {
        int ret = jdbcTemplate.update("INSERT INTO authentications (authDate, authTime, authIp, userId) VALUES (?, ?, ?, ?)",
                entity.getAuthDate(), entity.getAuthTime(), entity.getAuthIp(), entity.getUserId());

        if (ret == 0) {
            throw new UsersServiceException(UsersServiceExceptionEnum.AUTH_INFO_SAVE_ERROR);
        }
    }

    @Override
    public void update(Authentication entity) {
        // implementation is not required yet
    }

    @Override
    public void deleteById(Long id) {
        // implementation is not required yet
    }
}
