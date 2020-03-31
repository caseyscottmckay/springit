package com.springit.springit_backend.repository;

import java.util.Optional;
import com.springit.springit_backend.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

  Optional<Community> findByName(String communityName);

}
