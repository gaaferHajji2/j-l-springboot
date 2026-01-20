package com.jloka.authorization.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
    
    public PostDTO(Long id, String title, String content, 
                   LocalDateTime createdAt, Long userId, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.username = username;
    }
}