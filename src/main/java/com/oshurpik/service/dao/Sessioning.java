package com.oshurpik.service.dao;

import org.hibernate.Session;

public interface Sessioning {
    Session currentSession();    
}
