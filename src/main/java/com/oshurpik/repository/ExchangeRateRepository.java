package com.oshurpik.repository;

import com.oshurpik.entity.ExchangeRate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    @Query("SELECT r FROM ExchangeRate r  WHERE r.id.fromCurrency.name=:fromCurrencyName and r.id.toCurrency.name=:toCurrencyName")
    List<ExchangeRate> findByFromNameToName(@Param("fromCurrencyName") String fromCurrencyName, @Param("toCurrencyName") String toCurrencyName);
}