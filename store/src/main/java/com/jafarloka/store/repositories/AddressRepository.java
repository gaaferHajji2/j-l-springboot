package com.jafarloka.store.repositories;

import org.springframework.data.repository.CrudRepository;
import com.jafarloka.store.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long>{
    
}
