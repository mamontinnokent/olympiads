package com.olympiads.repository;

import com.olympiads.entity.Comment;
import com.olympiads.entity.Olympiad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOlympiad(Olympiad olympiad);
}
