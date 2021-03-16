package com.homemade.etl.repository.mapper;

import com.homemade.etl.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        Long objId = rs.getLong("id");
        user.setId(rs.wasNull() ? null : objId);

        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setDateCreated(rs.getTimestamp("date_created"));
        user.setDateLastModified(rs.getTimestamp("date_last_modified"));

        return user;
    }

}
