package com.springit.springit_backend.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

import java.util.List;
import com.springit.springit_backend.dto.CommunityDto;
import com.springit.springit_backend.exception.CommunityNotFoundException;
import com.springit.springit_backend.model.Community;
import com.springit.springit_backend.repository.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommunityService {

  private final CommunityRepository communityRepository;

  private final AuthService authService;

  @Transactional(readOnly = true)
  public List<CommunityDto> getAll() {
    return communityRepository.findAll()
            .stream()
            .map(this::mapToDto)
            .collect(toList());
  }

  @Transactional
  public CommunityDto save(CommunityDto communityDto){
    Community community = communityRepository.save(mapToCommunity(communityDto));
  communityDto.setId(community.getId());
  return communityDto;
  }

  @Transactional(readOnly = true)
  public CommunityDto getCommunity(Long id){
    Community community = communityRepository.findById(id).orElseThrow(()->new CommunityNotFoundException("Community not found"));
    return mapToDto(community);
  }
  private CommunityDto mapToDto(Community community){
    return CommunityDto.builder().name(community.getName())
            .id(community.getId())
            .postCount(community.getPosts().size())
            .build();
  }

  private Community mapToCommunity(CommunityDto communityDto){
    return Community.builder().name("/r/"+communityDto.getName())
            .description(communityDto.getDescription())
            .user(authService.getCurrentUser())
            .dateCreated(now()).build();
  }



}
