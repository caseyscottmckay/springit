package com.springit.springit_backend.service;

import java.util.List;
import com.springit.springit_backend.dto.PostRequest;
import com.springit.springit_backend.dto.PostResponse;

public interface PostService {

  public PostResponse getPost(Long id);

  public List<PostResponse> getAllPosts();

  public void save(PostRequest postRequest);

  public List<PostResponse> getPostsByCommunity(Long communityId);

  public List<PostResponse> getPostsByUsername(String username);

}
