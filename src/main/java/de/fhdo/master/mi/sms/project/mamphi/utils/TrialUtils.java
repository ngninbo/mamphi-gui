package de.fhdo.master.mi.sms.project.mamphi.utils;

import de.fhdo.master.mi.sms.project.mamphi.model.Zentrum;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.SQL_SCRIPT;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.GERMANY;

public class TrialUtils {

    public static void createDatabase(String databaseUrl) {
        StringBuilder query = new StringBuilder();

        File file = new File(SQL_SCRIPT);

        try (Scanner scanner = new Scanner(file);
             Connection connection = DriverManager.getConnection(databaseUrl)) {

            while(scanner.hasNext()) {
                query.append(scanner.nextLine());
            }

            Statement statement = connection.createStatement();

            statement.executeUpdate(query.toString());

            statement.close();

        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<Boolean, List<Zentrum>> partition(List<Zentrum> centerList) {

        return centerList.stream()
                .collect(Collectors.partitioningBy(center -> GERMANY.equals(center.getLand())));
    }

    public static Map<String, List<Zentrum>> grouping(List<Zentrum> centerList) {

        return centerList.stream()
                .collect(Collectors.groupingBy(Zentrum::getLand));
    }
}
