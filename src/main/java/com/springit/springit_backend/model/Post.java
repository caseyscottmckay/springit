package com.springit.springit_backend.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = SEQUENCE)
  private Long postId;

  @NotBlank(message = "Post Name cannot be empty or Null")
  private String postName;

  @Nullable
  private String url;

  @Lob//TODO maybe this eats up to much memory and better way to do this?
  private String description;

  private Integer voteCount;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private User user;

  private Instant dateCreated;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "id", referencedColumnName = "id")
  private Community community;

}
