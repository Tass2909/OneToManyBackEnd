package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;

import com.example.demo.exception.RessourceNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@GetMapping("/posts/{postId}/comments")
	public Page<Comment> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId, Pageable pageable) {
		return commentRepository.findByPostId(postId, pageable);
	}

	@PostMapping("/posts/{postId}/comments")
	public Comment createComment(@PathVariable(value = "postId") Long postId, @Validated @RequestBody Comment comment) {
		return postRepository.findById(postId).map(post -> {
			comment.setPost(post);
			return commentRepository.save(comment);
		}).orElseThrow(() -> new RessourceNotFoundException("PostId " + postId + " not found"));
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public Comment updateComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId, @Validated @RequestBody Comment commentRequest) {
		if (!postRepository.existsById(postId)) {
			throw new RessourceNotFoundException("PostId " + postId + " not found");
		}

		return commentRepository.findById(commentId).map(comment -> {
			comment.setText(commentRequest.getText());
			return commentRepository.save(comment);
		}).orElseThrow(() -> new RessourceNotFoundException("CommentId " + commentId + "not found"));
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {
		return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
			commentRepository.delete(comment);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new RessourceNotFoundException(
				"Comment not found with id " + commentId + " and postId " + postId));
	}
}