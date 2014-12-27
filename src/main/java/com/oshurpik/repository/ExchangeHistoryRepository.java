package com.oshurpik.repository;

import com.oshurpik.entity.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "exchange-history", path = "exchange-history")
public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Integer>, ExchangeHistoryRepositoryCustom {

}