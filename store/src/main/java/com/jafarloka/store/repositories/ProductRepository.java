package com.jafarloka.store.repositories;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.jafarloka.store.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // SQL or JPQL

    // SQL
    // @Query(value = "select * from products p where p.price between :min and :max order by p.name", nativeQuery = true)
    
    // JPQL
    @Query("select p from Product p where p.price between :min and :max order by p.name")
    List<Product> findProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
}
