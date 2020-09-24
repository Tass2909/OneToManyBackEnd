package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.exception.RessourceNotFoundException;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;

@Service
public class PostService {

	
	@Autowired
	private PostRepository postRepository;
	
	public Page<Post> getAllPostService(Pageable pageable) {
		return postRepository.findAll(pageable);
	}
	
	public Post getPostByIdService(Long postId) {
		Post post = new Post();
		post = postRepository.findById(postId)
				.orElseThrow(() -> new RessourceNotFoundException("Post not exist with the id: " + postId));
		return post;
	}
	
	public Post createPostService(Post post) {
		return postRepository.save(post);
	}
	
	public Post updatePostService(Long postId, Post postRequest) {
		Post post = new Post();
		post = postRepository.findById(postId)
				.orElseThrow(() -> new RessourceNotFoundException("Post not exist with the id: " + postId));
		post.setTitle(postRequest.getTitle());
		post.setDescription(postRequest.getDescription());
		post.setContent(postRequest.getContent());

		Post postUpdate = postRepository.save(post);
		return postUpdate;
	}
	
	public ResponseEntity<?> deletePostService(Long postId) {
		Post post = new Post();
		post = postRepository.findById(postId)
				.orElseThrow(() -> new RessourceNotFoundException("Post not exist with the id: " + postId));
		postRepository.delete(post);
		return ResponseEntity.ok().build();
	}
}
