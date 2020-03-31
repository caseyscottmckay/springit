package com.springit.springit_backend.service;

import java.util.List;
import java.util.stream.Collectors;
import com.springit.springit_backend.dto.PostRequest;
import com.springit.springit_backend.dto.PostResponse;
import com.springit.springit_backend.exception.CommunityNotFoundException;
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
public class PostServiceImpl implements PostService {

  private final AuthService authService;

  private final CommunityRepository communityRepository;

  private final PostMapper postMapper;

  private final PostRepository postRepostiory;

  private final UserRepository userRepository;

  @Override
  public PostResponse getPost(Long id) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    return postRepostiory.findAll()
            .stream()
            .map(postMapper::mapToDto)
            .collect(Collectors.toList());
  }

  @Override
  public void save(PostRequest postRequest) {
    Community community = communityRepository.findByName(postRequest.getCommunityName())
            .orElseThrow(()->new CommunityNotFoundException(postRequest.getCommunityName()));
    postRepostiory.save(postMapper.map(postRequest,community,authService.getCurrentUser()));
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByCommunity(Long communityId) {
    Community community = communityRepository.findById(communityId)
            .orElseThrow(()-> new CommunityNotFoundException(communityId.toString()));
    List<Post> posts = postRepostiory.findAllByCommunity(community);
    return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {

    User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new UsernameNotFoundException(username));
    return postRepostiory.findByUser(user)
            .stream()
            .map(postMapper::mapToDto)
            .collect(Collectors.toList());
  }

}
