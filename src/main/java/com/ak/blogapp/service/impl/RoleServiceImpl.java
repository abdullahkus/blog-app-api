package com.ak.blogapp.service.impl;

import com.ak.blogapp.entity.Role;
import com.ak.blogapp.mapper.RoleMapper;
import com.ak.blogapp.payload.RolePayload;
import com.ak.blogapp.repository.RoleRepository;
import com.ak.blogapp.response.RoleResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional
    public SuccessBaseResponse<RoleResponse> createRole(RolePayload payload) {
        Role role = roleMapper.payloadToEntity(payload);

        Role newRole = roleRepository.save(role);

        return SuccessBaseResponse.success(roleMapper.entityToResponse(newRole));
    }
}

