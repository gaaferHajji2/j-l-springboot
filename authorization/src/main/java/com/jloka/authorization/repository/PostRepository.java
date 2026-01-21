package com.jloka.authorization.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.jloka.authorization.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @NonNull Page<Post> findAll(Pageable pageable);
    
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user WHERE p.id = :id")
    Optional<Post> findByIdWithUser(@Param("id") Long id);
}
