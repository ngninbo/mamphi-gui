package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.annotation.Service;
import de.fhdo.master.mi.sms.project.mamphi.model.Country;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;

import java.util.List;

@Service(name = "Randomization Service")
public interface RandomizationWeekService {

    void update(RandomizationWeek randomizationWeek, int week);
    void update(RandomizationWeek randomizationWeek) throws NoSuchMethodException;
    List<RandomizationWeek> findAllByWeek(int week);
    List<RandomizationWeek> findAllRandomWeek();
    List<RandomizationWeek> findAllByWeekAndCountry(int week, Country country);
}
