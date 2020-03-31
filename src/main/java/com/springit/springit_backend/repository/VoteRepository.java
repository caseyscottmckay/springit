package com.springit.springit_backend.repository;

import java.util.Optional;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.User;
import com.springit.springit_backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

  Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
