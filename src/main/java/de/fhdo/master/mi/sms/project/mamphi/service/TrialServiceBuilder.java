package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.repository.CenterRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.InformedConsentRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.RandomizationWeekRepository;

import java.io.File;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.*;

public class TrialServiceBuilder {

    private String database;
    private CenterRepository centerRepository;
    private InformedConsentRepository informedConsentRepository;
    private RandomizationWeekRepository randomizationWeekRepository;

    private TrialServiceBuilder() {
    }

    public static TrialServiceBuilder init() {
        return new TrialServiceBuilder();
    }

    public TrialServiceBuilder withDatabase(String database) {
        this.database = database;
        return this;
    }

    public TrialServiceBuilder withCenterRepository() {
        this.centerRepository = new CenterRepository();
        return this;
    }

    public TrialServiceBuilder withInformedConsentRepository() {
        this.informedConsentRepository = new InformedConsentRepository();
        return this;
    }

    public TrialServiceBuilder withRandomizationWeekRepository() {
        this.randomizationWeekRepository = new RandomizationWeekRepository();
        return this;
    }

    public TrialServiceBuilder withInitialData() {
        File file = new File(database);

        if (!file.exists()) {
            this.centerRepository = (CenterRepository) new CenterRepository(database)
                    .createTable(CREATE_CENTER_TABLE)
                    .populate(TABLE_CENTER_INIT_DATA);

            this.informedConsentRepository = (InformedConsentRepository) new InformedConsentRepository(database)
                    .createTable(CREATE_INFORMED_CONSENT_TABLE)
                    .populate(TABLE_INFORMED_CONSENT_INIT_DATA);

            this.randomizationWeekRepository = new RandomizationWeekRepository(database).createTable().populate();
        }

        return this;
    }

    public TrialService build() {
        return new TrialServiceImpl(centerRepository, informedConsentRepository, randomizationWeekRepository);
    }
}
