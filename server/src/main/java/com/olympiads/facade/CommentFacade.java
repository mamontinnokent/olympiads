package com.olympiads.facade;

import com.olympiads.dto.CommentDTO;
import com.olympiads.entity.Comment;
import org.springframework.stereotype.Component;


@Component
public class CommentFacade {

    public CommentDTO commentToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .name(comment.getName())
                .surname(comment.getSurname())
                .message(comment.getMessage())
                .build();
    }
}
