package com.jafarloka.store.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jafarloka.store.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
