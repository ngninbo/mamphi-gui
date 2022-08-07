package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.Centre;
import de.fhdo.master.mi.sms.project.mamphi.model.RandomizationWeek;
import de.fhdo.master.mi.sms.project.mamphi.repository.CenterRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.InformedConsentRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.RandomizationWeekRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrialServiceTest {

    private static TrialService trialService;
    private static final CenterRepository centerRepository = mock(CenterRepository.class);
    private static final InformedConsentRepository informedConsentRepository = mock(InformedConsentRepository.class);
    private static final RandomizationWeekRepository randomizationWeekRepository = mock(RandomizationWeekRepository.class);

    @BeforeAll
    public static void beforeAll() {
        trialService = new TrialServiceImpl(centerRepository, informedConsentRepository, randomizationWeekRepository);
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void update() throws NoSuchMethodException {
        var rand = new RandomizationWeek();
        doThrow(new NoSuchMethodException()).when(randomizationWeekRepository).update(isA(RandomizationWeek.class));
        assertThrows(NoSuchMethodException.class, () -> trialService.update(rand));
        verify(randomizationWeekRepository, times(1)).update(eq(rand));
    }

    @ParameterizedTest
    @MethodSource("argCenterFactory")
    public void testUpdate(int centreId, String country, String place, String monitor, String trier) {
        doNothing().when(centerRepository).update(isA(Centre.class));
        Centre centre = new Centre(monitor, trier, place, country, centreId);
        trialService.update(centre);
        verify(centerRepository, times(1)).update(centre);
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
        doReturn(List.of()).when(centerRepository).findAll();
        List<Centre> centres = trialService.findAllCenter();
        assertThat(centres).isEqualTo(List.of());
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

    public static List<Arguments> argCenterFactory() {
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