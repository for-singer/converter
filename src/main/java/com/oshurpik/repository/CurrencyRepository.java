package com.oshurpik.repository;

import com.oshurpik.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Repository
@RepositoryRestResource(collectionResourceRel = "currency", path = "currency")
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    public Currency findByName(@Param("name") String name);
}