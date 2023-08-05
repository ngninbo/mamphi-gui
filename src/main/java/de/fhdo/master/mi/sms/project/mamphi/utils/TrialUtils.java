package de.fhdo.master.mi.sms.project.mamphi.utils;

import de.fhdo.master.mi.sms.project.mamphi.model.Centre;
import de.fhdo.master.mi.sms.project.mamphi.model.Country;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.fhdo.master.mi.sms.project.mamphi.utils.TrialStatements.SQL_SCRIPT;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.GERMANY;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.*;

public class TrialUtils {

    public static Map<Boolean, List<Centre>> partition(List<Centre> centerList) {

        return centerList.stream()
                .collect(Collectors.partitioningBy(center -> GERMANY.equals(center.getCountry())));
    }

    public static Map<String, List<Centre>> grouping(List<Centre> centerList) {

        return centerList.stream()
                .collect(Collectors.groupingBy(Centre::getCountry));
    }

    public static void createDatabase(String databaseUrl) throws SQLException, IOException {

        Connection connection = DriverManager.getConnection(databaseUrl);
        String query = new String(Files.readAllBytes(Paths.get(SQL_SCRIPT)));
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public static Predicate<String> fileExist() {
        return fileName -> new File(fileName).exists();
    }

    public static List<String> getRandomWeekOverviewOptions() {
        List<String> options = new ArrayList<>();

        options.add(RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL);
        options.add(String.format(RANDOM_WEEK_OVERVIEW_OPTION, 1));
        options.add(String.format(RANDOM_WEEK_OVERVIEW_OPTION, 2));

        Arrays.stream(Country.values()).forEach(country -> IntStream.rangeClosed(1, 2)
                .mapToObj(i -> String.format(RANDOM_WEEK_COUNTRY_OVERVIEW_OPTION, country.getFullCountryName(), i))
                .forEach(options::add));

        return options;
    }

    public static List<String> getConsentOverviewOptions() {
        return List.of(ALL_CONSENT_OPTION,
                MISSING_CONSENT_OVERVIEW_OPTION,
                INCOMPLETE_CONSENT_OVERVIEW_OPTION,
                INFORMED_CONSENT_AFTER_RANDOMIZATION_OVERVIEW_OPTION,
                INFORMED_CONSENT_OVERVIEW_OPTION,
                DECLINED_CONSENT_OVERVIEW_OPTION);
    }
}
