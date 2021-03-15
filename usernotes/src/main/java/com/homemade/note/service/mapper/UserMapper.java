package com.homemade.note.service.mapper;

import com.homemade.note.domain.User;
import com.homemade.note.entity.UserEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;


@Component
public class UserMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.classMap(UserEntity.class, User.class)
                .field("noteEntities", "notes")
                .byDefault()
                .register();
    }

}
