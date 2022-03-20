package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.*;
import de.fhdo.master.mi.sms.project.mamphi.repository.CenterRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.InformedConsentRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.RandomizationWeekRepository;

import java.util.List;

public class TrialServiceImpl implements TrialService {

    private final CenterRepository centerRepository;
    private final InformedConsentRepository informedConsentRepository;
    private final RandomizationWeekRepository randomizationWeekRepository;

    public TrialServiceImpl(CenterRepository centerRepository,
                            InformedConsentRepository informedConsentRepository,
                            RandomizationWeekRepository randomizationWeekRepository) {
        this.centerRepository = centerRepository;
        this.informedConsentRepository = informedConsentRepository;
        this.randomizationWeekRepository = randomizationWeekRepository;
    }

    @Override
    public void update(Zentrum center) {
        centerRepository.update(center);
    }

    @Override
    public void update(InformedConsent informedConsent) {
        informedConsentRepository.update(informedConsent);
    }

    @Override
    public void update(RandomizationWeek randomizationWeek, int week) {
        randomizationWeekRepository.update(randomizationWeek, week);
    }

    @Override
    public List<RandomizationWeek> findAllByWeek(int week) {
        return randomizationWeekRepository.findAllByWeek(week);
    }

    @Override
    public List<RandomizationWeek> findAllRandomWeek() {
        return randomizationWeekRepository.findAll();
    }

    @Override
    public List<RandomizationWeek> findAllByWeekAndLand(int week, Land land) {
        return randomizationWeekRepository.findAllByWeekAndLand(week, land);
    }

    @Override
    public List<Zentrum> findAllCenter() {
        return centerRepository.findAll();
    }

    @Override
    public List<Integer> findAllCenterIds() {
        return centerRepository.findAllCenterIDs();
    }

    @Override
    public List<Integer> findAllPatientID() {
        return centerRepository.findAllPatientID();
    }

    @Override
    public List<Zentrum> findAllCenter(Land land) {
        return centerRepository.findAllByLand(land);
    }

    @Override
    public List<InformedConsent> findAllInformedConsent() {
        return informedConsentRepository.findAll();
    }

    @Override
    public List<InformedConsent> findAllInformedConsent(Consent consent) {
        return informedConsentRepository.findAllByConsent(consent);
    }

    @Override
    public List<InformedConsent> findAllInformedConsent(boolean isInformed) {
        return informedConsentRepository.findAll(isInformed);
    }

    @Override
    public List<MonitorVisite> getMonitorVisitPlan(boolean isInvolved) {
        return centerRepository.getMonitorVisitPlan(isInvolved);
    }

    @Override
    public int nextId(Land land) {
        return centerRepository.nextId(land);
    }

    @Override
    public List<PatientCenter> findNumberOfPatientPerCenterByWeek(int week) {
        return centerRepository.findNumberOfPatientPerCenterByWeek(week);
    }

    @Override
    public List<PatientCenter> findNumberOfPatientPerCenterByAllWeek() {
        return centerRepository.findNumberOfPatientPerCenterByAllWeek();
    }

    @Override
    public List<PatientCenter> findNumberPatientPerCenterByLandByWeek(Land land, int week) {
        return centerRepository.findNumberPatientPerCenterByLandByWeek(land, week);
    }
}
