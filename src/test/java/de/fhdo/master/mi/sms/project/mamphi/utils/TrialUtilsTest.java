package de.fhdo.master.mi.sms.project.mamphi.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.*;
import static org.junit.jupiter.api.Assertions.*;

class TrialUtilsTest {

    @Test
    void getRandomWeekOverviewOptions() {

        List<String> actual = List.of(RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL,
                RANDOM_WEEK_ONE_OVERVIEW_OPTION,
                RANDOM_WEEK_TWO_OVERVIEW_OPTION,
                RANDOM_WEEK_ONE_GERMANY_OVERVIEW_OPTION,
                RANDOM_WEEK_TWO_GERMANY_OVERVIEW_OPTION,
                RANDOM_WEEK_ONE_ENGLAND_OVERVIEW_OPTION,
                RANDOM_WEEK_TWO_ENGLAND_OVERVIEW_OPTION);

        List<String> options = TrialUtils.getRandomWeekOverviewOptions();

        assertEquals(options, actual);
    }

    @Test
    void getConsentOverviewOptions() {
    }
}