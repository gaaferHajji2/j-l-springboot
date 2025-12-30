package com.jloka.jloka.services;

import com.jloka.jloka.models.*;
import com.jloka.jloka.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DataService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Transactional
    public void createSampleData() {
        // Create a new user with profile (One-to-One)
        User newUser = new User("new_user", "new@example.com");
        UserProfile profile = new UserProfile("New", "User", LocalDate.of(1995, 7, 20));
        newUser.setProfile(profile);
        
        // Assign roles (Many-to-Many)
        Role userRole = roleRepository.findByName("USER");
        Role editorRole = roleRepository.findByName("EDITOR");
        newUser.addRole(userRole);
        newUser.addRole(editorRole);
        
        userRepository.save(newUser);
        
        // Create a new post (One-to-Many)
        Post newPost = new Post("New Spring Boot Feature", "Exciting new features in Spring Boot 3.2...", newUser);
        
        // Add tags (Many-to-Many)
        Tag springTag = tagRepository.findByName("Spring Boot");
        Tag javaTag = tagRepository.findByName("Java");
        newPost.addTag(springTag);
        newPost.addTag(javaTag);
        
        // Add a comment
        Comment comment = new Comment("Looking forward to trying this!", newPost, newUser);
        newPost.addComment(comment);
        
        postRepository.save(newPost);
    }
    
    @Transactional(readOnly = true)
    public void displayUserData(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            System.out.println("User: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            
            // One-to-One
            if (user.getProfile() != null) {
                System.out.println("Profile: " + user.getProfile().getFirstName() + 
                                 " " + user.getProfile().getLastName());
            }
            
            // Many-to-Many: Roles
            System.out.println("Roles: ");
            user.getRoles().forEach(role -> System.out.println("  - " + role.getName()));
            
            // One-to-Many: Posts
            System.out.println("Posts (" + user.getPosts().size() + "):");
            user.getPosts().forEach(post -> {
                System.out.println("  - " + post.getTitle());
                
                // Many-to-Many: Tags for each post
                System.out.println("    Tags: " + 
                    String.join(", ", post.getTags().stream()
                        .map(Tag::getName)
                        .toList()));
                
                // One-to-Many: Comments for each post
                System.out.println("    Comments (" + post.getComments().size() + "):");
                post.getComments().forEach(comment -> {
                    System.out.println("      - " + comment.getContent() + 
                                     " (by " + comment.getUser().getUsername() + ")");
                });
            });
        });
    }
}
