package com.springit.springit_backend.mapper;

import com.springit.springit_backend.controller.CommentsDto;
import com.springit.springit_backend.model.Comment;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "text", source = "commentsDto.text")
  @Mapping(target = "dateCreated", expression = "java(java.time.Instant.now())")
  @Mapping(target = "post", source = "post")
  Comment map(CommentsDto commentsDto, Post post, User user);

  @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
  @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
  CommentsDto mapToDto(Comment comment);

}
