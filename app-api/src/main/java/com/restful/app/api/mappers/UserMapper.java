package com.restful.app.api.mappers;

import com.restful.app.api.dto.UserDto;
import com.restful.app.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserMapper extends AbstractMapper<User, UserDto>{

    private final ModelMapper mapper;

    public UserMapper(ModelMapper mapper) {
        super(User.class, UserDto.class, mapper);
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setId));
    }
}
