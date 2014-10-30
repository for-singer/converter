package com.oshurpik.repository;

import com.oshurpik.entity.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Integer>, ExchangeHistoryRepositoryCustom {

}