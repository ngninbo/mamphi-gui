package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.*;
import de.fhdo.master.mi.sms.project.mamphi.repository.CenterRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.InformedConsentRepository;
import de.fhdo.master.mi.sms.project.mamphi.repository.RandomizationWeekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrialServiceTest {

    private static TrialService trialService;
    private static final CenterRepository centerRepository = mock(CenterRepository.class);
    private static final InformedConsentRepository informedConsentRepository = mock(InformedConsentRepository.class);
    private static final RandomizationWeekRepository randomizationWeekRepository = mock(RandomizationWeekRepository.class);

    @BeforeEach
    public void setUp() {
        trialService = new TrialServiceImpl(centerRepository, informedConsentRepository, randomizationWeekRepository);
    }

    @Test
    @DisplayName("should throws NoSuchMethodException (not implemented) by attempt to update new RandomizationWeek entry")
    public void update() throws NoSuchMethodException {
        var rand = new RandomizationWeek();
        doThrow(new NoSuchMethodException()).when(randomizationWeekRepository).update(isA(RandomizationWeek.class));
        assertThrows(NoSuchMethodException.class, () -> trialService.update(rand));
        verify(randomizationWeekRepository, times(1)).update(eq(rand));
    }

    @ParameterizedTest
    @MethodSource("argCenterFactory")
    @DisplayName("should update centre given in following cases:")
    public void testUpdate(@AggregateWith(CenterAggregator.class) Centre centre) {
        doNothing().when(centerRepository).update(isA(Centre.class));
        trialService.update(centre);
        verify(centerRepository, times(1)).update(centre);
    }

    @Test
    @DisplayName("should update new consent entry")
    public void testUpdate1() {
        doNothing().when(informedConsentRepository).update(isA(InformedConsent.class));
        var consent = new InformedConsent();
        trialService.update(consent);
        verify(informedConsentRepository).update(eq(consent));
    }

    @ParameterizedTest(name = "Should return randomization list for the {0}. week")
    @CsvSource({"1", "2"})
    public void findAllByWeek(int week) {
        doReturn(List.of()).when(randomizationWeekRepository).findAllByWeek(isA(Integer.class));
        var list = trialService.findAllByWeek(week);
        assertThat(list).isEqualTo(List.of());
        verify(randomizationWeekRepository).findAllByWeek(week);
    }

    @Test
    @DisplayName("should return all RandomizationWeek entries")
    public void findAllRandomWeek() {
        doReturn(List.of()).when(randomizationWeekRepository).findAll();
        var list = trialService.findAllRandomWeek();
        assertThat(list).isEqualTo(List.of());
        verify(randomizationWeekRepository).findAll();
    }

    @ParameterizedTest(name = "should find all random week entries from {0}. week by country {1}")
    @CsvSource(value = {"1, DE", "2, DE", "1, GB", "2, GB"})
    public void findAllByWeekAndLand(int week, @ConvertWith(CountryConverter.class)  Country country) {
        doReturn(List.of()).when(randomizationWeekRepository).findAllByWeekAndLand(anyInt(), any(Country.class));
        var list = trialService.findAllByWeekAndCountry(week, country);
        assertThat(list).isEqualTo(List.of());
        verify(randomizationWeekRepository).findAllByWeekAndLand(eq(week), eq(country));
    }

    @Test
    @DisplayName("should return list of centres entries")
    public void findAllCenter() {
        doReturn(List.of()).when(centerRepository).findAll();
        List<Centre> centres = trialService.findAllCenter();
        assertThat(centres).isEqualTo(List.of());
        verify(centerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should return list of centres ids")
    public void findAllCenterIds() {
        doReturn(List.of()).when(centerRepository).findAllCenterIDs();
        var ids = trialService.findAllCenterIds();
        assertThat(ids).isEqualTo(List.of());
        verify(centerRepository).findAllCenterIDs();
    }

    @Test
    @DisplayName("should return list of patients ids")
    public void findAllPatientID() {
        doReturn(List.of()).when(centerRepository).findAllPatientID();
        var ids = trialService.findAllPatientID();
        assertThat(ids).isEqualTo(List.of());
        verify(centerRepository).findAllPatientID();
    }

    @ParameterizedTest(name = "should return all {0} informed consents")
    @EnumSource(value = Consent.class)
    public void findAllInformedConsent(Consent consent) {
        doReturn(List.of()).when(informedConsentRepository).findAllByConsent(isA(Consent.class));
        var consents = trialService.findAllInformedConsent(consent);
        assertThat(consents).isEqualTo(List.of());
        verify(informedConsentRepository).findAllByConsent(eq(consent));
    }

    @Test
    @DisplayName("should return all informed consents entry")
    public void testFindAllInformedConsent() {
        doReturn(List.of()).when(informedConsentRepository).findAll();
        var consents = trialService.findAllInformedConsent();
        assertThat(consents).isEqualTo(List.of());
        verify(informedConsentRepository).findAll();
    }

    @ParameterizedTest(name = "should return all informed consents with status {0}")
    @EnumSource(value = ConsentInformedStatus.class)
    public void findAllInformedConsentByStatus(ConsentInformedStatus status) {
        doReturn(List.of()).when(informedConsentRepository).findAll(isA(ConsentInformedStatus.class));
        var consents = trialService.findAllInformedConsent(status);
        assertThat(consents).isEqualTo(List.of());
        verify(informedConsentRepository).findAll(eq(status));
    }

    @Test
    public void nextId() {
        int id = 207;
        doReturn(id).when(centerRepository).nextId(isA(Country.class));
        int expectedId = trialService.nextIdByCountry(Country.GB);
        assertThat(expectedId).isEqualTo(id);
        verify(centerRepository).nextId(eq(Country.GB));
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