package com.homemade.note.repository;

import com.homemade.note.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Query("SELECT n FROM NoteEntity n WHERE n.userEntity.id = :userId")
    List<NoteEntity> getNotesByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE NoteEntity n SET n.title = :title, n.note= :note, n.dateLastModified = CURRENT_TIMESTAMP WHERE n.id = :id")
    int updateNote(@Param("id") Long id, @Param("title") String title, @Param("note") String note);
}
