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
}