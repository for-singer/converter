package com.oshurpik.service.impl;

import com.oshurpik.entity.Currency;
import com.oshurpik.service.dao.CurrencyDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CurrencyDaoImpl extends HibernateDao<Currency, Integer> implements CurrencyDao {
    
    @Override
    public Currency findByName(String name) {
        Currency currency = null;
        String sql = "FROM Currency c  WHERE c.name=:name";
        Query query = currentSession().createQuery(sql).setString("name", name);
        currency = (Currency)query.list().get(0);
            
        return currency;        
    }
}