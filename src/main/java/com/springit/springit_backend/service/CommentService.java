package com.springit.springit_backend.service;

import java.util.List;
import com.springit.springit_backend.controller.CommentsDto;
import com.springit.springit_backend.model.User;

public interface CommentService {

  void createComment(CommentsDto commentsDto);
  List<CommentsDto> getCommentByPost(Long postId);
  List<CommentsDto> getCommentsByUser(String userName);
  void sendCommentNotification(String message, User user);
}
