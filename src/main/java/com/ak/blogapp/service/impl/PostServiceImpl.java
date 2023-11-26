package com.ak.blogapp.service.impl;

import com.ak.blogapp.entity.Post;
import com.ak.blogapp.enums.EPostSortBy;
import com.ak.blogapp.exception.ResourceNotFoundException;
import com.ak.blogapp.mapper.PostMapper;
import com.ak.blogapp.payload.PostPayload;
import com.ak.blogapp.repository.PostRepository;
import com.ak.blogapp.response.PostResponse;
import com.ak.blogapp.response.result.PaginationResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public SuccessBaseResponse<PostResponse> createPost(PostPayload postPayload) {
        Post post = postMapper.payloadToEntity(postPayload);

        Post newPost = postRepository.save(post);

        return SuccessBaseResponse.success(postMapper.entityToResponse(newPost));
    }

    @Override
    @Transactional
    public SuccessBaseResponse<PaginationResponse<PostResponse>> getAllPosts(Integer pageNo, Integer pageSize, EPostSortBy sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy.getValue()).ascending()
                : Sort.by(sortBy.getValue()).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postList = postRepository.findAll(pageable);
        List<Post> listOfPostList = postList.getContent();

        List<PostResponse> responseList = postMapper.entityListToRepsonseList(listOfPostList);

        return SuccessBaseResponse.successWithPagination(
                pageNo,
                pageSize,
                postList.getTotalPages(),
                responseList
        );
    }

    @Override
    @Transactional
    public SuccessBaseResponse<PostResponse> getPostById(String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        PostResponse postResponse = postMapper.entityToResponse(post);

        return SuccessBaseResponse.success(postResponse);
    }

    @Override
    @Transactional
    public Post findPostById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    @Transactional
    public SuccessBaseResponse<PostResponse> updatePost(PostPayload postPayload, String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postMapper.updateEntityFromPayload(postPayload, post);

        Post updatedPost = postRepository.save(post);

        return SuccessBaseResponse.success(postMapper.entityToResponse(updatedPost));
    }

    @Override
    @Transactional
    public SuccessBaseResponse<String> deletePostById(String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);

        return SuccessBaseResponse.success("Post entity deleted successfully.");
    }

    @Override
    public boolean existsById(String id) {
        return postRepository.existsById(id);
    }
}
