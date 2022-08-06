package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.annotation.Service;
import de.fhdo.master.mi.sms.project.mamphi.repository.BaseRepository;

import java.io.IOException;
import java.sql.SQLException;

@Service
public interface TrialService extends CenterService, RandomizationWeekService, InformedConsentService {

    static void createDatabase(String databaseUrl) throws SQLException, IOException {

        BaseRepository.createDatabase(databaseUrl);
    }
}
