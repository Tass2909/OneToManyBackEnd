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
import com.example.demo.model.Post;
import com.example.demo.service.PostService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/posts")
	public Page<Post> getAllPosts(Pageable pageable) {
		return postService.getAllPostService(pageable);
	}

	@GetMapping("/posts/{postId}")
	public Post getPostById(@PathVariable Long postId) {
		return postService.getPostByIdService(postId);
	}

	@PostMapping("/posts")
	public Post createPost(@Validated @RequestBody Post post) {
		return postService.createPostService(post);
	}

	@PutMapping("/posts/{postId}")
	public Post updatePost(@PathVariable Long postId, @Validated @RequestBody Post postRequest) {
		return postService.updatePostService(postId, postRequest);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		return postService.deletePostService(postId);
	}

}
