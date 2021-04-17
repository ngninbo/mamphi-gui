# mamphi-admin-gui

This is a sample project implementing a graphical user interface application for the management of a clinical trial.

The Goal of the so implemented application is to allow authorized administrator to easilly manupulate the data of the trial stored in a data base.

In this version of the application the mentioned data are query from a SQLite Data Base.

# SQLite Database

For now, application used an sqlite Database saved locally. The data stored in this database had been extracted from Excel-Sheets programmatically.
This database can also be reproduiced using the sql script `Mamphi.sql` provided with this repository.

In that case, some sql statements used to fetched the data from the database, which are saved the script `Anweisungen.sql`, have to be updated 
in order to manage the data properly. They may be look like those used in another version of the application using a mysql database, 
see [mamphi_query_mysql.sql](https://github.com/ngninbo/mamphi-admin-gui/blob/main/mamphi_query_mysql.sql)

The recommended SQLite Editor for this purpose will be [DB Browser for SQLite](https://sqlitebrowser.org/).

# Get Started

After cloning the project, open the it in an editor and setup the url (e.g. path to the SQLite data base) for connection in the class `Fetcher.java`
properly.

To run the application use the following command. 

```shell
clean javafx:run
```

For Signing in use the following cred-itials:

username: demo

password: demo
