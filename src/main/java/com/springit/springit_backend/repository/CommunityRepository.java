package com.springit.springit_backend.repository;

import java.util.Optional;
import com.springit.springit_backend.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

  Optional<Community> findByName(String communityName);

}
