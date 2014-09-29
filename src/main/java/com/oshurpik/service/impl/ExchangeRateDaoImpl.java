package com.oshurpik.service.impl;

import com.oshurpik.entity.ExchangeRate;
import com.oshurpik.entity.ExchangeRatePK;
import com.oshurpik.service.dao.ExchangeRateDao;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ExchangeRateDaoImpl extends HibernateDao<ExchangeRate, ExchangeRatePK> implements ExchangeRateDao {

    @Override
    public List<ExchangeRate> findByFromNameToName(String fromCurrencyName, String toCurrencyName) {
        List<ExchangeRate> list = null;
        String sql = "SELECT r FROM ExchangeRate r  WHERE r.id.fromCurrency.name=:fromCurrency and r.id.toCurrency.name=:toCurrency";
        Query query = currentSession().createQuery(sql).setString("fromCurrency", fromCurrencyName).setString("toCurrency", toCurrencyName);
        list = query.list();

        return list;
    }
}