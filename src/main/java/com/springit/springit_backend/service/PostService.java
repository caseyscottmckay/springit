package com.springit.springit_backend.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import com.springit.springit_backend.dto.PostRequest;
import com.springit.springit_backend.dto.PostResponse;
import com.springit.springit_backend.exception.CommunityNotFoundException;
import com.springit.springit_backend.exception.PostNotFoundException;
import com.springit.springit_backend.mapper.PostMapper;
import com.springit.springit_backend.model.Community;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import com.springit.springit_backend.repository.CommunityRepository;
import com.springit.springit_backend.repository.PostRepository;
import com.springit.springit_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

  private final PostRepository postRepository;

  private final CommunityRepository communityRepository;

  private final UserRepository userRepository;

  private final AuthService authService;

  private final PostMapper postMapper;

  public void save(PostRequest postRequest) {
    Community community = communityRepository.findByName(postRequest.getCommunityName())
            .orElseThrow(() -> new CommunityNotFoundException(postRequest.getCommunityName()));
    postRepository.save(postMapper.map(postRequest, community, authService.getCurrentUser()));
  }

  @Transactional(readOnly = true)
  public PostResponse getPost(Long id) {
    Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(id.toString()));
    return postMapper.mapToDto(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    return postRepository.findAll()
            .stream()
            .map(postMapper::mapToDto)
            .collect(toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByCommunity(Long communityId) {
    Community community = communityRepository.findById(communityId)
            .orElseThrow(() -> new CommunityNotFoundException(communityId.toString()));
    List<Post> posts = postRepository.findAllByCommunity(community);
    return posts.stream().map(postMapper::mapToDto).collect(toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    return postRepository.findByUser(user)
            .stream()
            .map(postMapper::mapToDto)
            .collect(toList());
  }

}
