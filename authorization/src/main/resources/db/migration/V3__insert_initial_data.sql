-- Insert default admin user (password: admin123)
INSERT INTO users (username, password, email) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iK9io4XeD/A9WMPoYqC2W5J5N7xW', 'admin@example.com')
ON DUPLICATE KEY UPDATE username = username;

-- Insert sample posts for admin user
INSERT INTO posts (title, content, user_id, created_at) VALUES
('Welcome to Our Blog', 'This is the first post on our amazing blog platform!', 1, NOW() - INTERVAL 5 DAY),
('Getting Started with Spring Boot', 'Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.', 1, NOW() - INTERVAL 4 DAY),
('JWT Authentication Best Practices', 'Learn how to implement secure JWT authentication in your applications.', 1, NOW() - INTERVAL 3 DAY),
('Database Design Principles', 'Understanding proper database design is crucial for application performance.', 1, NOW() - INTERVAL 2 DAY),
('REST API Design Guide', 'A comprehensive guide to designing RESTful APIs that are intuitive and easy to use.', 1, NOW() - INTERVAL 1 DAY)
ON DUPLICATE KEY UPDATE title = title;

-- Insert additional test users (passwords are all 'password123')
INSERT IGNORE INTO users (username, password, email) VALUES
('john_doe', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iK9io4XeD/A9WMPoYqC2W5J5N7xW', 'john.doe@example.com'),
('jane_smith', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iK9io4XeD/A9WMPoYqC2W5J5N7xW', 'jane.smith@example.com'),
('bob_johnson', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iK9io4XeD/A9WMPoYqC2W5J5N7xW', 'bob.johnson@example.com')
ON DUPLICATE KEY UPDATE username = username;

-- Insert additional sample posts
INSERT IGNORE INTO posts (title, content, user_id, created_at) VALUES
('Understanding Microservices', 'Microservices architecture allows building scalable and maintainable applications.', 2, NOW() - INTERVAL 10 DAY),
('Docker Containerization', 'Learn how to containerize your applications using Docker for better deployment.', 2, NOW() - INTERVAL 9 DAY),
('Kubernetes Orchestration', 'Kubernetes helps in automating deployment, scaling, and management of containerized applications.', 2, NOW() - INTERVAL 8 DAY),
('CI/CD Pipeline Setup', 'Setting up Continuous Integration and Continuous Deployment pipelines for automated testing and deployment.', 3, NOW() - INTERVAL 7 DAY),
('Cloud Computing Benefits', 'Exploring the advantages of moving your infrastructure to the cloud.', 3, NOW() - INTERVAL 6 DAY),
('Machine Learning Basics', 'An introduction to machine learning concepts and algorithms.', 4, NOW() - INTERVAL 5 DAY),
('Web Security Essentials', 'Essential security practices every web developer should know.', 4, NOW() - INTERVAL 4 DAY)
ON DUPLICATE KEY UPDATE title = title;
