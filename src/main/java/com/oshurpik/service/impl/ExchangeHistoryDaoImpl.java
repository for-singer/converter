package com.oshurpik.service.impl;

import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.service.dao.ExchangeHistoryDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ExchangeHistoryDaoImpl extends HibernateDao<ExchangeHistory, Integer> implements ExchangeHistoryDao {
    @Override
    public List<ExchangeHistory> findByFromCurrencyName(String name) {
        List<ExchangeHistory> results = null;
        Criteria cr = currentSession().createCriteria(ExchangeHistory.class, "exchangeHistory");
        cr.createAlias("exchangeHistory.fromCurrency", "fromCurrency");
        cr.add(Restrictions.eq("fromCurrency.name", name));
        results = cr.list();  
        return results;        
    }
   
    @Override
    public List getNumberOfTransationsForEachFromCurrency() {
        List results = null;
        Criteria cr = currentSession().createCriteria(ExchangeHistory.class);

        cr.setProjection(Projections.projectionList()
            .add(Projections.groupProperty("fromCurrency").as("fromCurrency"))
            .add(Projections.groupProperty("toCurrency").as("toCurrency"))
            .add(Projections.count("toCurrency")));

        results = cr.list();
        return results;        
    }
    
    @Override
    public List getFromCurrencyWithMaxTransactionCount() {
        List results = null;

        Criteria cr = currentSession().createCriteria(ExchangeHistory.class);

        cr.setProjection(Projections.projectionList()
            .add(Projections.groupProperty("fromCurrency").as("fromCurrency"))
            .add(Projections.groupProperty("toCurrency").as("toCurrency"))
            .add(Projections.count("toCurrency").as("toCurrencyCount")));
        cr.addOrder(Order.desc("toCurrencyCount"));

        results = cr.list();
            
        for(int i = 1; i < results.size(); i++) {
            Long currCount = (Long)(((Object[])((Object)(results.get(i - 1))))[2]);
            Long nextCount = (Long)(((Object[])((Object)(results.get(i))))[2]);
            if (currCount > nextCount) {
                results = results.subList(0, i);
                break;
            }
        }
                    
        return results;
    }
}