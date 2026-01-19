package com.jloka.jloka.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.jloka.jloka.models.Customer;

@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface RestCustomerRepository extends PagingAndSortingRepository<Customer, Long>, 
    CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String name);
}