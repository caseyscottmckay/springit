package com.springit.springit_backend.controller;

import java.util.List;
import javax.validation.Valid;
import com.springit.springit_backend.dto.CommunityDto;
import com.springit.springit_backend.service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
public class CommunityController {

  private final CommunityService communityService;

  @GetMapping
  public List<CommunityDto> getAllCommunities() {
    return communityService.getAll();
  }

  @GetMapping("/{id}")
  public CommunityDto getCommunity(@PathVariable Long id) {
    return communityService.getCommunity(id);
  }

  @PostMapping
  public CommunityDto create(@RequestBody @Valid CommunityDto communityDto) {
    return communityService.save(communityDto);
  }

}
