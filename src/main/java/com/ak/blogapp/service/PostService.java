package com.ak.blogapp.service;

import com.ak.blogapp.entity.Post;
import com.ak.blogapp.enums.EPostSortBy;
import com.ak.blogapp.payload.PostPayload;
import com.ak.blogapp.response.PostResponse;
import com.ak.blogapp.response.result.PaginationResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;

public interface PostService {
    SuccessBaseResponse<PostResponse> createPost(PostPayload postPayload);

    SuccessBaseResponse<PaginationResponse<PostResponse>> getAllPosts(Integer pageNo, Integer pageSize, EPostSortBy sortBy, String sortDir);

    SuccessBaseResponse<PostResponse> getPostById(String id);

    Post findPostById(String id);

    SuccessBaseResponse<PostResponse> updatePost(PostPayload postPayload, String id);

    SuccessBaseResponse<String> deletePostById(String id);

    boolean existsById(String id);
}