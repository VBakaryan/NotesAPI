package com.homemade.note.repository;

import com.homemade.note.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
}
