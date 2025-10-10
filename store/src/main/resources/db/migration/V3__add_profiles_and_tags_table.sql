-- Create Tags table
CREATE TABLE Tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create Profiles table
CREATE TABLE Profiles (
    id BIGINT PRIMARY KEY,
    bio TEXT,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    loyalty_points INT UNSIGNED DEFAULT 0 CHECK (loyalty_points >= 0),
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create User Tags Table
CREATE TABLE user_tags (
    user_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE ON UPDATE CASCADE
);