package com.springit.springit_backend.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;
import com.springit.springit_backend.dto.PostRequest;
import com.springit.springit_backend.dto.PostResponse;
import com.springit.springit_backend.service.PostServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

  private final PostServiceImpl postServiceImpl;

  @PostMapping
  public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
    postServiceImpl.save(postRequest);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    return status(HttpStatus.OK).body(postServiceImpl.getAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
    return status(HttpStatus.OK).body(postServiceImpl.getPost(id));
  }

  @GetMapping("by-community/{id}")
  public ResponseEntity<List<PostResponse>> getPostsByCommunity(Long id) {
    return status(HttpStatus.OK).body(postServiceImpl.getPostsByCommunity(id));
  }

  @GetMapping("by-user/{name}")
  public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
    return status(HttpStatus.OK).body(postServiceImpl.getPostsByUsername(username));
  }

}
