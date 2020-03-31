package com.springit.springit_backend.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.springit.springit_backend.dto.PostRequest;
import com.springit.springit_backend.dto.PostResponse;
import com.springit.springit_backend.model.Community;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import com.springit.springit_backend.repository.CommentRepository;
import com.springit.springit_backend.repository.VoteRepository;
import com.springit.springit_backend.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private VoteRepository voteRepository;

  @Autowired
  private AuthService authService;

  @Mapping(target = "dateCreated", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  @Mapping(target = "community", source = "community")
  @Mapping(target = "voteCount", constant = "0")
  public abstract Post map(PostRequest postRequest, Community community, User user);

  @Mapping(target = "id", source = "postId")
  @Mapping(target = "communityName", source = "community.name")
  @Mapping(target = "userName", source = "user.username")
  @Mapping(target = "commentCount", expression = "java(commentCount(post))")
  @Mapping(target = "duration", expression = "java(getDuration(post))")
  public abstract PostResponse mapToDto(Post post);

  Integer commentCount(Post post) {
    return commentRepository.findByPost(post).size();
  }

  String getDuration(Post post) {
    return TimeAgo.using(post.getDateCreated().toEpochMilli());
  }

}
