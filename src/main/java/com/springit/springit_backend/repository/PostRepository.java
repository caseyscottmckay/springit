package com.springit.springit_backend.repository;

import java.util.List;
import com.springit.springit_backend.model.Community;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByCommunity(Community community);

  List<Post> findByUser(User user);

}
