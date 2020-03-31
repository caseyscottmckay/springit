package com.springit.springit_backend.mapper;

import com.springit.springit_backend.dto.PostRequest;
import com.springit.springit_backend.dto.PostResponse;
import com.springit.springit_backend.model.Community;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface PostMapper {

  @Mapping(target="dateCreated", expression = "java(java.time.Instant.now())")
  @Mapping(target = "community", source = "community")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "description", source = "postRequest.description")
  Post map(PostRequest postRequest, Community community, User user);

  @Mapping(target = "id", source = "postId")
  @Mapping(target = "postName", source = "postName")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "communityName", source = "community.name")
  @Mapping(target = "userName", source = "user.username")
  PostResponse mapToDto(Post post);


}
