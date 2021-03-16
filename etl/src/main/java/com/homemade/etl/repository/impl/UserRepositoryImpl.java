package com.homemade.etl.repository.impl;

import com.homemade.etl.domain.User;
import com.homemade.etl.repository.UserRepository;
import com.homemade.etl.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository {

    // JDBC Template instance
    private JdbcTemplate jdbcTemplate;

    /**
     * Initializes a new instance of the class.
     *
     * @param jdbcTemplate
     */
    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return jdbcTemplate.query("SELECT u.id, u.email, u.password, u.date_created, u.date_last_modified FROM t_user AS u", new UserMapper());
        } catch (Exception ex) {
            log.error("Failed to get users list, error {}", ex);

            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<User> getRecentUsersWithPagination(int page, int size, Date from, Date to) {
        try {
            return jdbcTemplate.query("SELECT u.id, u.email, u.password, u.date_created, u.date_last_modified FROM t_user AS u " +
                    "WHERE CAST(date_created AS DATE) < ? AND CAST(date_created AS DATE) >= ? " +
                    "OR CAST(date_last_modified AS DATE) < ? AND CAST(date_last_modified AS DATE) >= ? LIMIT ? OFFSET ? ", new Object[] {to, from, to, from, size, page * size}, new UserMapper());
        } catch (Exception ex) {
            log.error("Failed to get users list, error {}", ex);

            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
