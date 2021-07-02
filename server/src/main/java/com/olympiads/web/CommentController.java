package com.olympiads.web;

import com.olympiads.dto.CommentDTO;
import com.olympiads.entity.Comment;
import com.olympiads.facade.CommentFacade;
import com.olympiads.payload.response.MessageResponse;
import com.olympiads.service.CommentService;
import com.olympiads.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentFacade commentFacade;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @GetMapping("/{olympiadId}/all")
    public ResponseEntity<List<CommentDTO>> allCommentByOlimpiad(@PathVariable String id) {
        List<CommentDTO> comments = commentService.getAllByOlympiadId(Long.parseLong(id)).stream()
                .map(commentFacade::commentToCommentDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{olympiadId}/")
    public ResponseEntity<Object> createOlympiad(@Valid @RequestBody CommentDTO commentDTO,
                                                @PathVariable("olympiadId") String olympiadId,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Comment comment = commentService.saveComment(Long.parseLong(olympiadId), commentDTO, principal);
        CommentDTO createdComment = commentFacade.commentToCommentDTO(comment);

        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("/{olympiadId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("olympiadId") String olympiadId) {
        commentService.deleteComment(Long.parseLong(olympiadId));
        return ResponseEntity.ok(new MessageResponse("Comment was deleted"));
    }
}