package com.springit.springit_backend.service;

import static com.springit.springit_backend.model.VoteType.UPVOTE;

import java.util.Optional;
import com.springit.springit_backend.dto.VoteDto;
import com.springit.springit_backend.exception.PostNotFoundException;
import com.springit.springit_backend.exception.SpringitException;
import com.springit.springit_backend.model.Post;
import com.springit.springit_backend.model.Vote;
import com.springit.springit_backend.repository.PostRepository;
import com.springit.springit_backend.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

  private final AuthService authService;

  private final PostRepository postRepository;

  private final VoteRepository voteRepository;

  @Transactional
  public void vote(VoteDto voteDto) {
    Post post = postRepository.findById(voteDto.getPostId())
            .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
    Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
    if (voteByPostAndUser.isPresent() &&
            voteByPostAndUser.get().getVoteType()
                    .equals(voteDto.getVoteType())) {
      throw new SpringitException("You have already "
              + voteDto.getVoteType() + "'d for this post");
    }
    if (UPVOTE.equals(voteDto.getVoteType())) {
      post.setVoteCount(post.getVoteCount() + 1);
    } else {
      post.setVoteCount(post.getVoteCount() - 1);
    }
    voteRepository.save(mapToVote(voteDto, post));
    postRepository.save(post);
  }

  private Vote mapToVote(VoteDto voteDto, Post post) {
    return Vote.builder()
            .voteType(voteDto.getVoteType())
            .post(post)
            .user(authService.getCurrentUser())
            .build();
  }

}
