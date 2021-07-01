package com.olympiads.web;

import com.olympiads.facade.CommentFacade;
import com.olympiads.service.CommentService;
import com.olympiads.validation.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}