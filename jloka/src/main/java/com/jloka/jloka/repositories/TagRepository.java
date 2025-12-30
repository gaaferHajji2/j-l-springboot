package com.jloka.jloka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jloka.jloka.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public Tag findByName(String name);
}
