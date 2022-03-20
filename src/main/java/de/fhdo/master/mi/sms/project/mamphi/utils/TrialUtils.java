package de.fhdo.master.mi.sms.project.mamphi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static de.fhdo.master.mi.sms.project.mamphi.utils.MamphiStatements.SQL_SCRIPT;

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
}
