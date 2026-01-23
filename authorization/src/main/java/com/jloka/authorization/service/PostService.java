package com.jloka.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.jloka.authorization.dto.PostDTO;
import com.jloka.authorization.model.Post;
import com.jloka.authorization.model.User;
import com.jloka.authorization.repository.PostRepository;
import com.jloka.authorization.repository.UserRepository;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        // Using pagination for list access - no eager loading of relations
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(post -> new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUser().getId(),
                post.getUser().getUsername()));
    }

    public Optional<PostDTO> getPostById(Long id) {
        // Using JOIN FETCH to eagerly load user relation only when accessing by ID
        Optional<Post> post = postRepository.findByIdWithUser(id);

        return post.map(p -> new PostDTO(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                p.getCreatedAt(),
                p.getUser().getId(),
                p.getUser().getUsername()));
    }

    public Post createPost(@NonNull Post post) {
        post.setUser(getCurrentUser());        
        return postRepository.save(post);
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<User> user = userRepository.findByUsername(username);
            if(user == null) {
                throw new RuntimeException("No authenticated user found");
            }
            return user.get();
        }

        throw new RuntimeException("No authenticated user found");
    }

}