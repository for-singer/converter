package com.oshurpik.repository.impl;

import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.repository.ExchangeHistoryRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class ExchangeHistoryRepositoryImpl implements ExchangeHistoryRepositoryCustom {
    @PersistenceContext
    EntityManager em;
    
    @Override
    public List<ExchangeHistory> findByFromCurrencyName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<ExchangeHistory> cq = cb.createQuery(ExchangeHistory.class);
        Root<ExchangeHistory> eh = cq.from(ExchangeHistory.class);
        cq.where(cb.equal(eh.get("fromCurrency").get("name"), name));
        Query query = em.createQuery(cq);
        List<ExchangeHistory> result = query.getResultList();

        return result;
    }

    @Override
    public List getNumberOfTransationsForEachFromCurrency() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root eh = cq.from(ExchangeHistory.class);

        Expression fc = eh.get("fromCurrency").get("name");
        Expression tc = eh.get("toCurrency").get("name");
        Expression c = cb.count(fc);
        
        cq.multiselect(fc.alias("fromCurrency"), tc.alias("toCurrency"), c.alias("toCurrencyCount"));
        cq.groupBy(fc, tc);

        Query query = em.createQuery(cq);

        List result = query.getResultList();

        return result;
    }

    @Override
    public List getFromCurrencyWithMaxTransactionCount() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root eh = cq.from(ExchangeHistory.class);

        Expression fc = eh.get("fromCurrency").get("name");
        Expression tc = eh.get("toCurrency").get("name");
        Expression c = cb.count(fc);
        
        cq.multiselect(fc.alias("fromCurrency"), tc.alias("toCurrency"), c.alias("toCurrencyCount"));
        cq.groupBy(fc, tc);
        cq.orderBy(cb.desc(c));

        Query query = em.createQuery(cq);

        List result = query.getResultList();
        
        for(int i = 1; i < result.size(); i++) {
            Long currCount = (Long)(((Object[])(result.get(i - 1)))[2]);
            Long nextCount = (Long)(((Object[])(result.get(i)))[2]);
            if (currCount > nextCount) {
                result = new ArrayList(result.subList(0, i));
                break;
            }
        }

        return result;
    }
}