package com.restful.app.api.mappers;

import com.restful.app.api.dto.UserDetailDto;
import com.restful.app.entity.UserDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserDetailMapper extends AbstractMapper<UserDetail, UserDetailDto> {

    private final ModelMapper mapper;


    public UserDetailMapper(ModelMapper mapper) {
        super(UserDetail.class, UserDetailDto.class, mapper);
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(UserDetailDto.class, UserDetail.class)
                .addMappings(m -> m.skip(UserDetail::setId));
    }
}
