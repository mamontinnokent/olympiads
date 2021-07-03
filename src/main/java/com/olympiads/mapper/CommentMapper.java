package com.olympiads.mapper;

import com.olympiads.dto.all.CommentDTO;
import com.olympiads.entity.Comment;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper {

    public CommentDTO commentToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .name(comment.getName())
                .surname(comment.getSurname())
                .message(comment.getMessage())
                .build();
    }
}
