-- Insert sample roles
INSERT INTO roles (name, description) VALUES 
('ADMIN', 'System Administrator with full access'),
('USER', 'Regular user with basic access'),
('EDITOR', 'Can create and edit content'),
('MODERATOR', 'Can moderate user content');

-- Insert sample users
INSERT INTO users (username, email) VALUES 
('john_doe', 'john@example.com'),
('jane_smith', 'jane@example.com'),
('bob_wilson', 'bob@example.com'),
('alice_jones', 'alice@example.com');

-- Insert user profiles
INSERT INTO user_profiles (user_id, first_name, last_name, date_of_birth, phone_number) VALUES 
(1, 'John', 'Doe', '1990-05-15', '+1234567890'),
(2, 'Jane', 'Smith', '1985-08-22', '+0987654321'),
(3, 'Bob', 'Wilson', '1992-03-10', '+1122334455'),
(4, 'Alice', 'Jones', '1988-11-30', '+5566778899');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 1), -- john_doe is ADMIN
(1, 2), -- john_doe is also USER
(2, 3), -- jane_smith is EDITOR
(2, 2), -- jane_smith is also USER
(3, 2), -- bob_wilson is USER
(4, 4), -- alice_jones is MODERATOR
(4, 2); -- alice_jones is also USER

-- Insert sample tags
INSERT INTO tags (name) VALUES 
('Spring Boot'),
('Database'),
('Java'),
('Tutorial'),
('Web Development'),
('ORM'),
('MariaDB');

-- Insert sample posts
INSERT INTO posts (title, content, author_id) VALUES 
('Getting Started with Spring Boot', 'Spring Boot makes it easy to create stand-alone...', 1),
('Database Relationships Explained', 'Learn about One-to-One, One-to-Many, and Many-to-Many...', 2),
('JPA Best Practices', 'Here are some best practices when working with JPA...', 1),
('Flyway Migration Guide', 'How to use Flyway for database version control...', 3);

-- Associate tags with posts
INSERT INTO post_tags (post_id, tag_id) VALUES 
(1, 1), (1, 3), (1, 5), -- Spring Boot, Java, Web Development
(2, 2), (2, 6),        -- Database, ORM
(3, 1), (3, 3), (3, 6), -- Spring Boot, Java, ORM
(4, 1), (4, 2), (4, 7); -- Spring Boot, Database, MariaDB

-- Insert sample comments
INSERT INTO comments (content, post_id, user_id) VALUES 
('Great tutorial! Very helpful.', 1, 2),
('Thanks for sharing this knowledge.', 1, 3),
('I have a question about section 3...', 2, 4),
('This solved my problem, thank you!', 3, 2),
('Looking forward to more content like this.', 4, 1);