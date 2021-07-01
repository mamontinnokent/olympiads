package com.olympiads.service;

import com.olympiads.dto.CommentDTO;
import com.olympiads.entity.Comment;
import com.olympiads.entity.Olympiad;
import com.olympiads.entity.User;
import com.olympiads.exceptions.OlympiadNotFoundException;
import com.olympiads.repository.CommentRepository;
import com.olympiads.repository.OlympiadRepository;
import com.olympiads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final OlympiadRepository olympiadRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, OlympiadRepository olympiadRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.olympiadRepository = olympiadRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Long olympiadId, CommentDTO commentDTO, Principal principal) {
        User user = getCurrentUser(principal);
        Olympiad olympiad = olympiadRepository.findById(olympiadId)
                .orElseThrow(() -> new OlympiadNotFoundException("Olympiad with id " + olympiadId + " not found"));

        Comment comment = new Comment();

        comment.setOlympiad(olympiad);
        comment.setUserId(user.getId());
        comment.setMessage(commentDTO.getMessage());
        comment.setName(user.getName());
        comment.setSurname(user.getSurname());

        return commentRepository.save(comment);
    }

    public User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));
    }
}
