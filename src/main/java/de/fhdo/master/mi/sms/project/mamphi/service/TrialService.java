package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.*;

import java.util.List;

public interface TrialService {

    void update(Centre center);
    void update(InformedConsent informedConsent);
    void update(RandomizationWeek randomizationWeek, int week);
    void update(RandomizationWeek randomizationWeek) throws NoSuchMethodException;
    List<RandomizationWeek> findAllByWeek(int week);
    List<RandomizationWeek> findAllRandomWeek();
    List<RandomizationWeek> findAllByWeekAndLand(int week, Country country);
    List<Centre> findAllCenter();
    List<String> findAllCenterIds();
    List<Integer> findAllPatientID();
    List<Centre> findAllCenter(Country country);
    List<InformedConsent> findAllInformedConsent();
    List<InformedConsent> findAllInformedConsent(Consent consent);
    List<InformedConsent> findAllInformedConsent(boolean isInformed);
    List<MonitorVisit> getMonitorVisitPlan(boolean isInvolved);
    int nextId(Country country);
    List<PatientCenter> findNumberOfPatientPerCenterByWeek(int week);
    List<PatientCenter> findNumberOfPatientPerCenterByAllWeek();

    List<PatientCenter> findNumberPatientPerCenterByLandByWeek(Country country, int week);
}
