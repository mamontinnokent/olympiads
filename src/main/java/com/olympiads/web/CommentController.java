package com.olympiads.web;

import com.olympiads.dto.all.CommentDTO;
import com.olympiads.dto.response.MessageResponse;
import com.olympiads.entity.Comment;
import com.olympiads.mapper.CommentMapper;
import com.olympiads.service.CommentService;
import com.olympiads.validation.ResponseErrorValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping("/{olympiadId}")
    public ResponseEntity<List<CommentDTO>> allByOlimpiad(@PathVariable String id) {
        List<CommentDTO> comments = commentService.getAllByOlympiadId(Long.parseLong(id)).stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{olympiadId}/")
    public ResponseEntity<Object> create(@Valid @RequestBody CommentDTO commentDTO,
                                         @PathVariable("olympiadId") String olympiadId,
                                         BindingResult bindingResult,
                                         Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Comment comment = commentService.saveComment(Long.parseLong(olympiadId), commentDTO, principal);
        CommentDTO createdComment = commentMapper.commentToCommentDTO(comment);

        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("/{olympiadId}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("olympiadId") String olympiadId) {
        commentService.deleteComment(Long.parseLong(olympiadId));
        return ResponseEntity.ok(new MessageResponse("Comment was deleted"));
    }
}