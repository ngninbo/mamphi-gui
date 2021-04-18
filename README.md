# mamphi-gui

This is a sample project implementing a graphical user interface application for the management of a clinical trial.

The Goal of the so implemented application is to allow authorized administrator to easily manupulate the data of the trial stored in a data base.

In this version of the application the mentioned data are query from a SQLite Data Base.

## SQLite Database reproduction

As mentioned before, this application get data from a sqlite Database and display it. 
So in order to run the application, the database have to be created and saved locally. 

This can be archived using the script named `mamphidb.db.sql` provided with this repository. 

For time saving, the SQLite Editor [DB Browser for SQLite](https://sqlitebrowser.org/) is recommanded for this purpose.

By interest, the samples SQL statements used to get manage the data from the database can be found in the script `Statements.sql`.

## Get Started


Clone the project.

```shell
git clone https://github.com/ngninbo/mamphi-gui.git
```


Import the project in our favorite IDE.


Setup the url (e.g. path to the SQLite data base) in the class `FetchData.java` from the package `de.fhdo.master.mi.sms.project.mamphi.repository`. 
This URL is needed by the SQLite JDBC driver for connection.

```Java
// TODO: Setup the path to your sqlite data base here

private final String url = "jdbc:sqlite:C:\\mamphi\\mamphidb.db";
```

Run the application using the following command.

```shell
clean javafx:run
```

Sign in using the following credentials:


```text
username: demo

password: demo
```

