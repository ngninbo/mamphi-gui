package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.*;

import java.util.List;

public interface TrialService {

    void update(Zentrum center);
    void update(InformedConsent informedConsent);
    void update(RandomizationWeek randomizationWeek, int week);
    List<RandomizationWeek> findAllByWeek(int week);
    List<RandomizationWeek> findAllRandomWeek();
    List<RandomizationWeek> findAllByWeekAndLand(int week, Land land);
    List<Zentrum> findAllCenter();
    List<Integer> findAllCenterIds();
    List<Integer> findAllPatientID();
    List<Zentrum> findAllCenter(Land land);
    List<InformedConsent> findAllInformedConsent();
    List<InformedConsent> findAllInformedConsent(Consent consent);
    List<InformedConsent> findAllInformedConsent(boolean isInformed);
    List<MonitorVisite> getMonitorVisitPlan(boolean isInvolved);
    int nextId(Land land);
    List<PatientCenter> findNumberOfPatientPerCenterByWeek(int week);
    List<PatientCenter> findNumberOfPatientPerCenterByAllWeek();

    List<PatientCenter> findNumberPatientPerCenterByLandByWeek(Land land, int week);
}
