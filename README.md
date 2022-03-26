# mamphi-gui

# Table of content
1. [Description](#Description)
2. [SQLite Database reproduction](#database)
3. [Automatically creating Database in Code](#database2)
4. [Get Started](#start)
5. [TODO](#TODO)

## Description
This is a sample project implementing a graphical user interface application for the management of a clinical trial.

The Goal of the so implemented application is to allow authorized administrator to easily manipulate the data of the trial stored in a data base.

In this version of the application the mentioned data are query from a SQLite Data Base.

## Setup database manually <a name="database"></a>

As mentioned before, this application manage data from a sqlite Database and display it. 
So in order to run the application, the database could be created manually and 
the database filename passed to the application as program argument.

This could be archived using the script named `mamphidb.db.sql` provided with this repository. 

For time saving, the SQLite Editor [DB Browser for SQLite](https://sqlitebrowser.org/) is recommended for this purpose.

For interested reader, the samples SQL statements used to manage the data from the database can be found in the script `Statements.sql`.

## Automatically creating Database in Code <a name="database2"></a>
The required database is created automatically with the following code snippet:

```java
public class TrialUtils {

    public static void createDatabase(String database) {
        String databaseUrl = String.format(TRIAL_DB_URL, database);
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
```

## Get Started <a name="start"></a>

Clone the project.

```shell
git clone https://github.com/ngninbo/mamphi-gui.git
```


Import the project in our favorite IDE.


Set up the database filename (e.g. name of the SQLite file containing the database) in `src/main/java/de/fhdo/master/mi/sms/project/mamphi/utils/TrialStatements.java`
This file name is needed for a successful SQLite JDBC driver connection.

Run the application using the following command.

```shell
mvn clean install javafx:run
```

Sign in using the following credentials:


```text
username: demo

password: demo
```

After started up, the application may look as followed:

![screenshot](screenshot.png)

# TODO
- Refactor code
- Errors handling
- Implement delete functionality
- On Monitor plan view, make sure the visit date are displayed each in a row
- Use FXML to Create the User Interface
- Extract styling/Extend `application.css` file
- Pass database file name as command line argument by editing `Run Configuration` or
- Define database file name as property in `pom.xml` file
- Make sure the application use the configured database file name on start up
- Documentation
- Last but not least, Unit tests

