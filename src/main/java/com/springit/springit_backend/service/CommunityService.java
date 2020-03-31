package com.springit.springit_backend.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

import java.util.List;
import com.springit.springit_backend.dto.CommunityDto;
import com.springit.springit_backend.exception.CommunityNotFoundException;
import com.springit.springit_backend.mapper.CommunityMapper;
import com.springit.springit_backend.model.Community;
import com.springit.springit_backend.repository.CommunityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CommunityService {

  private final CommunityRepository communityRepository;

  private final CommunityMapper communityMapper;

  @Transactional(readOnly = true)
  public List<CommunityDto> getAll() {
    return communityRepository.findAll()
            .stream()
            .map(communityMapper::mapCommunityToDto)
            .collect(toList());
  }

  @Transactional
  public CommunityDto save(CommunityDto communityDto){
    Community community = communityRepository.save(communityMapper.mapDtoToCommunity(communityDto));
  communityDto.setId(community.getId());
  return communityDto;
  }

  @Transactional(readOnly = true)
  public CommunityDto getCommunity(Long id){
    Community community = communityRepository.findById(id).orElseThrow(()->new CommunityNotFoundException("Community not found"));
    return communityMapper.mapCommunityToDto(community);
  }



}
