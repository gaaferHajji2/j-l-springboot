package com.jloka.jloka.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.jloka.jloka.models.Customer;
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
    Customer findById(long id);
}