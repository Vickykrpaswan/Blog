package com.blog.controllers;

import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    private ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
        CommentDto comment1 = this.commentService.createComment(comment, postId);
        return new ResponseEntity<>(comment1, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    private ResponseEntity<?> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(Map.of("message","Successfully deleted"),HttpStatus.OK);
    }
}
