package com.springit.springit_backend.dto;

import com.springit.springit_backend.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

  private VoteType voteType;

  private Long postId;

}
