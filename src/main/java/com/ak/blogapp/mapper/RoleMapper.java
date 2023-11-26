package com.ak.blogapp.mapper;

import com.ak.blogapp.entity.Role;
import com.ak.blogapp.payload.RolePayload;
import com.ak.blogapp.response.RoleResponse;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    Role payloadToEntity(RolePayload payload);

    RoleResponse entityToResponse(Role role);
}
