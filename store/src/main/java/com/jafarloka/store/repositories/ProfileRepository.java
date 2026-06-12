package com.jafarloka.store.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jafarloka.store.models.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    
}
