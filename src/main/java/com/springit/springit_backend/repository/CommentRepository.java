package com.springit.springit_backend.repository;

import java.util.List;
import com.springit.springit_backend.model.Comment;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);

}
