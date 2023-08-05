package de.fhdo.master.mi.sms.project.mamphi.service;

import java.util.List;

public interface CommonService<T> {

    void update(T t);
    List<T> findAll();
}
