package com.ak.blogapp.service;

import com.ak.blogapp.payload.CommentPayload;
import com.ak.blogapp.response.CommentResponse;
import com.ak.blogapp.response.result.SuccessBaseResponse;

import java.util.List;

public interface CommentService {
    SuccessBaseResponse<CommentResponse> createComment(String postId, CommentPayload commentPayload);

    SuccessBaseResponse<List<CommentResponse>> getCommentsByPostId(String postId);

    SuccessBaseResponse<CommentResponse> getCommentById(String postId, String commentId);

    SuccessBaseResponse<CommentResponse> updateComment(String postId, String commentId, CommentPayload commentPayload);

    SuccessBaseResponse<String> deleteComment(String postId, String commentId);
}
