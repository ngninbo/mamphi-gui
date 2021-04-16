# mamphi-admin-gui

This is a sample project implementing a graphical user interface application for the management of a clinical trial.

The Goal of the so implemented application is to allow authorized administrator to easilly manupulate the data of the trial stored in a data base.

In this version of the application the mentioned data are query from a SQLite Data Base.

# Get Started

After cloning the project, create the data base using the sql script `mamphi.sql`.

Then, open the project in an editor and make sure you set the url (e.g. path to the SQLite data base) for connection in the class `Fetcher.java`
properly.

To run the application use the following command. 

```shell
clean javafx:run
```

For Signing in use the following cred-itials:

username: demo

password: demo
