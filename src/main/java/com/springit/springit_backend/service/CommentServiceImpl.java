package com.springit.springit_backend.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import com.springit.springit_backend.controller.CommentsDto;
import com.springit.springit_backend.exception.PostNotFoundException;
import com.springit.springit_backend.mapper.CommentMapper;
import com.springit.springit_backend.model.Comment;
import com.springit.springit_backend.model.NotificationEmail;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import com.springit.springit_backend.repository.CommentRepository;
import com.springit.springit_backend.repository.PostRepository;
import com.springit.springit_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

  private static final String POST_URL = "";

  private final AuthService authService;
  private final CommentMapper commentMapper;
  private final CommentRepository commentRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Override
  public void createComment(CommentsDto commentsDto) {
    Post post = postRepository.findById(commentsDto.getPostId())
            .orElseThrow(()-> new PostNotFoundException(commentsDto.getPostId().toString()));
    Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
    commentRepository.save(comment);
    String message = mailContentBuilder.build(post.getUser().getUsername()+ " posted a comment on your post." + POST_URL);
    sendCommentNotification(message,post.getUser());
  }

  public List<CommentsDto> getCommentByPost(Long postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId.toString()));
    return commentRepository.findByPost(post)
            .stream()
            .map(commentMapper::mapToDto)
            .collect(toList());
  }

  public List<CommentsDto> getCommentsByUser(String userName) {
    User user = userRepository.findByUsername(userName)
            .orElseThrow(() -> new UsernameNotFoundException(userName));
    return commentRepository.findAllByUser(user)
            .stream()
            .map(commentMapper::mapToDto)
            .collect(toList());
  }

   public void sendCommentNotification(String message, User user) {
    mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
  }

}