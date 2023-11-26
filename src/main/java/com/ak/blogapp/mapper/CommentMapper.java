package com.ak.blogapp.mapper;

import com.ak.blogapp.entity.Comment;
import com.ak.blogapp.payload.CommentPayload;
import com.ak.blogapp.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper
public interface CommentMapper {
    Comment payloadToEntity(CommentPayload commentPayload);

    CommentResponse entityToResponse(Comment comment);

    void updateEntityFromPayload(CommentPayload commentPayload, @MappingTarget Comment comment);

    List<CommentResponse> entityListToRepsonseList(List<Comment> commentList);
}
