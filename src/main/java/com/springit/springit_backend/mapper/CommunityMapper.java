package com.springit.springit_backend.mapper;

import java.util.List;
import com.springit.springit_backend.dto.CommunityDto;
import com.springit.springit_backend.model.Community;
import com.springit.springit_backend.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommunityMapper {

  @Mapping(target="numberOfPosts", expression="java(mapPosts(community.getPosts()))")
  CommunityDto mapCommunityToDto(Community community);

  default Integer mapPosts(List<Post> numberOfPosts){
    return numberOfPosts.size();
  }

  @InheritInverseConfiguration
  @Mapping(target = "posts", ignore=true)
  Community mapDtoToCommunity(CommunityDto community);

}
