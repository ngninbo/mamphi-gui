package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.*;

import java.util.List;

public interface CenterService {

    void update(Centre center);
    List<Centre> findAllCenter();
    List<String> findAllCenterIds();
    List<Integer> findAllPatientID();
    List<Centre> findAllCenter(Country country);
    int nextIdByCountry(Country country);
    List<PatientCenter> findNumberOfPatientPerCenterByWeek(int week);
    List<PatientCenter> findNumberOfPatientPerCenterByAllWeek();

    List<PatientCenter> findNumberPatientPerCenterByCountryByWeek(Country country, int week);
    List<MonitorVisit> getMonitorVisitPlan();
}
