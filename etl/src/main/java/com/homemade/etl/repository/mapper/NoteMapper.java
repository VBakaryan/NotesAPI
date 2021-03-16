package com.homemade.etl.repository.mapper;

import com.homemade.etl.domain.Note;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class NoteMapper implements RowMapper<Note> {

    @Override
    public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
        Note note = new Note();
        Long objId = rs.getLong("id");
        note.setId(rs.wasNull() ? null : objId);

        note.setNote(rs.getString("note"));
        note.setTitle(rs.getString("title"));
        note.setUserId(rs.getLong("user_id"));
        note.setDateCreated(rs.getTimestamp("date_created"));
        note.setDateLastModified(rs.getTimestamp("date_last_modified"));

        return note;
    }

}
