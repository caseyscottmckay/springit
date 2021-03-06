package com.springit.springit_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {

  private Long id;

  private String name;

  private String description;

  private Integer numberOfPosts;

}
