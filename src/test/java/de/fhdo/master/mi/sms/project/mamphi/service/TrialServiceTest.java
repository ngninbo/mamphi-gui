package de.fhdo.master.mi.sms.project.mamphi.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.*;

class TrialServiceTest {

    private static TrialService trialService;
    private static String database = "test.db";

    @BeforeAll
    static void beforeAll() {
        trialService = TrialServiceBuilder.init()
                .withDatabase(database)
                .withCenterRepository()
                .withInformedConsentRepository()
                .withRandomizationWeekRepository()
                .build();
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void update() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testUpdate1() {

    }

    @Test
    void findAllByWeek() {
    }

    @Test
    void findAllRandomWeek() {
    }

    @Test
    void findAllByWeekAndLand() {
    }

    @Test
    void findAllCenter() {
    }

    @Test
    void findAllCenterIds() {
    }

    @Test
    void findAllPatientID() {
    }

    @Test
    void testFindAllCenter() {
    }

    @Test
    void findAllInformedConsent() {
    }

    @Test
    void testFindAllInformedConsent() {
    }

    @Test
    void testFindAllInformedConsent1() {
    }

    @Test
    void getMonitorVisitPlan() {
    }

    @Test
    void nextId() {
    }

    @Test
    void findNumberOfPatientPerCenterByWeek() {
    }

    @Test
    void findNumberOfPatientPerCenterByAllWeek() {
    }

    @Test
    void findNumberPatientPerCenterByLandByWeek() {
    }

    static List<Arguments> argCenterFactory() {
        return List.of(
                arguments(102, "D", "Essen", "Müller", "Bruch"),
                arguments(103, "D", "München", "Moser", "Wittmann"),
                arguments(104, "D", "Herne", "Kwiatkowski", "Wittmann"),
                arguments(105, "D", "Hannover", "Meyer", "Lange"),
                arguments(106, "D", "Köln", "Brettschneider", "Bruch"),
                arguments(107, "D", "Bremen", "Van Gool", "Lange"),
                arguments(108, "D", "Hamburg", "Talmann-Berg", "Lange"),
                arguments(109, "D", "Stuttgart", "Fischer", "Bruch"),
                arguments(110, "D", "Lepzig", "Obermeier", "Bruch"),
                arguments(201, "GB", "London", "McDonald", "Gordon"),
                arguments(202, "GB", "London", "Priest", "Thatcher"),
                arguments(203, "GB", "Manchester", "Down", "Gordon"),
                arguments(204, "GB", "Brighton", "Feldham", "Gordon"),
                arguments(205, "GB", "Leeds", "Harnister", "Thatcher"));
    }
}