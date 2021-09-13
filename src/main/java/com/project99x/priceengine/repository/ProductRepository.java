package com.project99x.priceengine.repository;

import com.project99x.priceengine.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
