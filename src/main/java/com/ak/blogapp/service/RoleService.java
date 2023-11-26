package com.ak.blogapp.service;

import com.ak.blogapp.payload.RolePayload;
import com.ak.blogapp.response.RoleResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;

public interface RoleService {
    SuccessBaseResponse<RoleResponse> createRole(RolePayload name);
}
