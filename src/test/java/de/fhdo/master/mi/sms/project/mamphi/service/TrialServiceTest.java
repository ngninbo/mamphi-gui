package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.*;

public class TrialServiceTest {

    private static TrialService trialService;
    private static String database = "test.db";

    @BeforeAll
    public static void beforeAll() {
        trialService = TrialServiceBuilder.init()
                .withDatabase(database)
                .withCenterRepository()
                .withInformedConsentRepository()
                .withRandomizationWeekRepository()
                .build();
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void update() {
        assertThrows(NoSuchMethodException.class, () -> trialService.update(new RandomizationWeek()));
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testUpdate1() {

    }

    @Test
    public void findAllByWeek() {
    }

    @Test
    public void findAllRandomWeek() {
    }

    @Test
    public void findAllByWeekAndLand() {
    }

    @Test
    public void findAllCenter() {
    }

    @Test
    public void findAllCenterIds() {
    }

    @Test
    public void findAllPatientID() {
    }

    @Test
    public void testFindAllCenter() {
    }

    @Test
    public void findAllInformedConsent() {
    }

    @Test
    public void testFindAllInformedConsent() {
    }

    @Test
    public void testFindAllInformedConsent1() {
    }

    @Test
    public void getMonitorVisitPlan() {
    }

    @Test
    public void nextId() {
    }

    @Test
    public void findNumberOfPatientPerCenterByWeek() {
    }

    @Test
    public void findNumberOfPatientPerCenterByAllWeek() {
    }

    @Test
    public void findNumberPatientPerCenterByLandByWeek() {
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