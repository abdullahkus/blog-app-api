package com.ak.blogapp.service.impl;

import com.ak.blogapp.entity.Comment;
import com.ak.blogapp.entity.Post;
import com.ak.blogapp.exception.AssociationException;
import com.ak.blogapp.exception.ResourceNotFoundException;
import com.ak.blogapp.mapper.CommentMapper;
import com.ak.blogapp.payload.CommentPayload;
import com.ak.blogapp.repository.CommentRepository;
import com.ak.blogapp.response.CommentResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;
import com.ak.blogapp.service.CommentService;
import com.ak.blogapp.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostService postService;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, PostService postService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.postService = postService;
    }

    @Override
    @Transactional
    public SuccessBaseResponse<CommentResponse> createComment(String postId, CommentPayload commentPayload) {
        Post post = postService.findPostById(postId);

        Comment comment = commentMapper.payloadToEntity(commentPayload);
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return SuccessBaseResponse.success(
                commentMapper.entityToResponse(newComment)
        );
    }

    @Override
    @Transactional
    public SuccessBaseResponse<List<CommentResponse>> getCommentsByPostId(String postId) {
        boolean existsPost = postService.existsById(postId);
        if (!existsPost) {
            throw new ResourceNotFoundException("Post", "PostId", postId);
        }

        List<Comment> commentList = commentRepository.findByPostId(postId);


        return SuccessBaseResponse.success(
                commentMapper.entityListToRepsonseList(commentList)
        );
    }

    @Override
    @Transactional
    public SuccessBaseResponse<CommentResponse> getCommentById(String postId, String commentId) {
        Post post = postService.findPostById(postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new AssociationException("Comment does not belongs to post");
        }

        return SuccessBaseResponse.success(
                commentMapper.entityToResponse(comment)
        );
    }

    @Override
    @Transactional
    public SuccessBaseResponse<CommentResponse> updateComment(String postId, String commentId, CommentPayload commentPayload) {
        Post post = postService.findPostById(postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new AssociationException("Comment does not belongs to post");
        }

        commentMapper.updateEntityFromPayload(commentPayload, comment);

        Comment updatedComment = commentRepository.save(comment);

        return SuccessBaseResponse.success(commentMapper.entityToResponse(updatedComment));
    }

    @Override
    @Transactional
    public SuccessBaseResponse<String> deleteComment(String postId, String commentId) {
        Post post = postService.findPostById(postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new AssociationException("Comment does not belongs to post");
        }

        commentRepository.delete(comment);

        return SuccessBaseResponse.success("Comment entity deleted successfully.");
    }
}