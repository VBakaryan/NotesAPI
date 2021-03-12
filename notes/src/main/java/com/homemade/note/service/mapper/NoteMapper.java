package com.homemade.note.service.mapper;

import com.homemade.note.domain.Note;
import com.homemade.note.entity.NoteEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;


@Component
public class NoteMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.classMap(NoteEntity.class, Note.class)
                .field("userEntity.id", "userId")
                .byDefault()
                .register();
    }

}
