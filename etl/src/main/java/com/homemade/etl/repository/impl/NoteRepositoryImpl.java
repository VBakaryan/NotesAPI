package com.homemade.etl.repository.impl;

import com.homemade.etl.domain.Note;
import com.homemade.etl.repository.NoteRepository;
import com.homemade.etl.repository.mapper.NoteMapper;
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
public class NoteRepositoryImpl implements NoteRepository {

    // JDBC Template instance
    private JdbcTemplate jdbcTemplate;

    /**
     * Initializes a new instance of the class.
     *
     * @param jdbcTemplate
     */
    @Autowired
    public NoteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Note> getAllNotes() {
        try {
            return jdbcTemplate.query("SELECT n.id, n.title, n.note, n.user_id, n.date_created, n.date_last_modified FROM t_note AS n", new NoteMapper());
        } catch (Exception ex) {
            log.error("Failed to get notes list, error {}", ex);

            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Note> getRecentNotesWithPagination(int page, int size, Date from, Date to) {
        try {
            return jdbcTemplate.query("SELECT n.id, n.title, n.note, n.user_id, n.date_created, n.date_last_modified FROM t_note AS n " +
                    "WHERE CAST(date_created AS DATE) < ? AND CAST(date_created AS DATE) >= ? " +
                    "OR CAST(date_last_modified AS DATE) < ? AND CAST(date_last_modified AS DATE) >= ? LIMIT ? OFFSET ? ", new Object[]{to, from, to, from, size, page * size}, new NoteMapper());
        } catch (Exception ex) {
            log.error("Failed to get notes list, error {}", ex);

            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
