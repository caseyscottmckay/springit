package com.springit.springit_backend.controller;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {

  private Long id;
  private Long postId;
  private Instant dateCreated;
  private String text;
  private String userName;
}
