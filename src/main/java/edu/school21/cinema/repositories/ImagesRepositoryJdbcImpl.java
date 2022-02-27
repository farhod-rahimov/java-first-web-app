package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.services.UsersServiceException;
import edu.school21.cinema.services.UsersServiceExceptionEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ImagesRepositoryJdbcImpl implements ImagesRepository {

    private JdbcTemplate jdbcTemplate;

    public ImagesRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class ImageMapper implements RowMapper<Image> {
        @Override
        public Image mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Image(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getFloat(5),
                    resultSet.getString(6),
                    resultSet.getLong(7));
        }
    }

    @Override
    public List<Image> findAllByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM images WHERE userId = ?",
                new ImagesRepositoryJdbcImpl.ImageMapper(), new Object[]{userId});
    }

    @Override
    public Optional<Image> findByUniqueNameAndUserId(String uniqueName, Long userId) {
        Image image = null;

        if (!uniqueName.isEmpty()) {
            image = jdbcTemplate.query("SELECT * FROM images WHERE uniqueName = ? AND userId = ?",
                            new ImagesRepositoryJdbcImpl.ImageMapper(), new Object[]{uniqueName, userId})
                    .stream().findAny().orElse(null);
        }
        return Optional.ofNullable(image);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        // implementation is not required yet
    }

    @Override
    public Optional<Image> findById(Long id) {
        Image image = jdbcTemplate.query("SELECT * FROM images WHERE id = ?",
                        new ImagesRepositoryJdbcImpl.ImageMapper(), new Object[]{id})
                .stream().findAny().orElse(null);

        return Optional.ofNullable(image);
    }

    @Override
    public List<Image> findAll() {
        return jdbcTemplate.query("SELECT * FROM images", new ImagesRepositoryJdbcImpl.ImageMapper());
    }

    @Override
    public void save(Image entity) {
        int ret = jdbcTemplate.update("INSERT INTO images (originalName, uniqueName, imagePath, imageSize, MIME, userId) " +
                "VALUES (?, ?, ?, ?, ?, ?)", entity.getOriginalName(), entity.getUniqueName(), entity.getImagePath(),
                entity.getImageSize(), entity.getMIME(), entity.getUserId());

        if (ret == 0) {
            throw new UsersServiceException(UsersServiceExceptionEnum.IMAGE_SAVE_ERROR);
        }
    }

    @Override
    public void update(Image entity) {
        // implementation is not required yet
    }

    @Override
    public void deleteById(Long id) {
        // implementation is not required yet
    }
}
