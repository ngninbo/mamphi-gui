package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.repository.CenterRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.InformedConsentRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.RandomizationWeekRepository;
import de.fhdo.master.mi.sms.project.mamphi.utils.TrialUtils;

import java.io.File;
import java.util.function.Predicate;

import static de.fhdo.master.mi.sms.project.mamphi.utils.TrialStatements.TRIAL_DB_URL;

public class TrialServiceBuilder {

    private String database;
    private String databaseUrl;
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
        databaseUrl = String.format(TRIAL_DB_URL, database);
        return this;
    }

    public TrialServiceBuilder withCenterRepository() {
        this.centerRepository = new CenterRepository().setDatabaseUrl(databaseUrl);
        return this;
    }

    public TrialServiceBuilder withInformedConsentRepository() {
        this.informedConsentRepository = new InformedConsentRepository().setDatabaseUrl(this.databaseUrl);
        return this;
    }

    public TrialServiceBuilder withRandomizationWeekRepository() {
        this.randomizationWeekRepository = new RandomizationWeekRepository().setDatabaseUrl(this.databaseUrl);
        return this;
    }

    public TrialServiceBuilder createDatabase() {
        Predicate<String> fileExist = (fileName) -> new File(fileName).exists();

        if (fileExist.negate().test(database)) {
            TrialUtils.createDatabase(databaseUrl);
        }

        return this;
    }

    public TrialService build() {
        return new TrialServiceImpl(centerRepository, informedConsentRepository, randomizationWeekRepository);
    }
}
