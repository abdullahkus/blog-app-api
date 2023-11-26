package com.ak.blogapp.mapper;

import com.ak.blogapp.entity.Post;
import com.ak.blogapp.payload.PostPayload;
import com.ak.blogapp.response.CommentResponse;
import com.ak.blogapp.response.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {CommentResponse.class})
public interface PostMapper {
    Post payloadToEntity(PostPayload postPayload);

    @Mapping(source = "comments", target = "comments")
    PostResponse entityToResponse(Post post);

    void updateEntityFromPayload(PostPayload postPayload, @MappingTarget Post post);

    List<PostResponse> entityListToRepsonseList(List<Post> postList);
}
