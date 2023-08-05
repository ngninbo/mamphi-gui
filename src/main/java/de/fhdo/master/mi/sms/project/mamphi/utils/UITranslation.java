package de.fhdo.master.mi.sms.project.mamphi.utils;

import de.fhdo.master.mi.sms.project.mamphi.model.Country;

public class UITranslation {

    public static final String MAMPHI_ADMINISTRATION_GUI_TITLE = "Mamphi Administration GUI";
    public static final String WELCOME_TXT = "Welcome";
    public static final String LOGIN_BUTTON_TOOLTIP_TXT = "Einloggen und Studie verwalten";
    public static final String CONSENT_BUTTON_LABEL = "Patientenliste";
    public static final String CONSENT_BUTTON_TOOLTIP_TXT = "Einwilligungen verwalten";
    public static final String CENTER_BUTTON_LABEL = "Zentrumsliste";
    public static final String CENTER_BUTTON_TOOLTIP_TXT = "Zentren verwalten";
    public static final String RANDOM_WEEK_BTN_LABEL = "Wochenliste";
    public static final String RANDOM_WEEK_TOOLTIP_TXT = "Wochenliste verwalten";
    public static final String MONITORING_PLAN_BTN_LABEL = "Monitoring Plan";
    public static final String MONITORING_PLAN_TOOLTIP_TXT = "Monitorplan anzeigen und verwalten";
    public static final String SIGN_OUT_BTN_LABEL = "Sign out";
    public static final String SIGN_OUT_BTN_TOOLTIP_TXT = "Ausloggen";
    public static final String SIGN_IN_ERROR_MSG = "Sign in data incorrect";
    public static final String MONITOR_PLAN_OVERVIEW_TXT = "Plan wann welches Monitor welches Zentrum besuchen soll.";
    public static final String CENTER_FORM_TEXT = "Neues Zentrum anlegen: ";
    public static final String SAVE_BTN_LABEL = "Speichern";
    public static final String DELETE_BTN_LABEL = "Eintrag löschen";
    public static final String COUNTRY_CHOICE_BTN_LABEL = "Land wählen";
    public static final String VISIT_COLUMN_LABEL = "Besuch Termine";
    public static final String CHOICE_PROMPT_TEXT = "Bitte wählen";
    public static final String DATE_FORMAT_TXT = "TT-MM-JJJJ";
    public static final String ENTRY_SAVE_BTN_LABEL = "Eintrag Speichern";
    public static final String NUMBER_PATIENT_PER_CENTER_ALL_WEEK_OVERVIEW_LABEL = "Anzahl Patient pro Zentrum \nalle Wochen";
    public static final String NUMBER_PATIENT_PER_CENTER_GERMANY_WEEK_OVERVIEW_LABEL = "Anzahl Patienten pro deutsches Zentrum \nin Woche %s";
    public static final String NUMBER_PATIENT_PER_CENTER_ENGLAND_WEEK_OVERVIEW_LABEL = "Anzahl Patienten pro britisches Zentrum \nin Woche %s";
    public static final String FILTER_LABEL = "Filtern: ";
    public static final String GERMANY = "Deutschland";
    public static final String ENGLAND = "Großbritannien";
    public static final String YES = "ja";
    public static final String NO = "nein";
    public static final String RANDOM_WEEK_OVERVIEW_OPTION = "Angaben zur Randomisierung in der %s. Woche anzeigen";
    public static final String RANDOM_WEEK_OVERVIEW_TITLE = "Angaben zur Randomisierung in der Woche %s";
    public static final String RANDOM_WEEK_OVERVIEW_LABEL = "Anzahl Patienten pro Zentrum \nin Woche %s";
    public static final String RANDOM_WEEK_COUNTRY_OVERVIEW_OPTION = "Anzahl der Patienten pro Zentrum in %s in der %s. Woche anzeigen";
    public static final String VIEW_ANOTHER_RANDOM_WEEK_PROMPT_TXT = "Andere wöchentliche Liste anzeigen lassen";
    public static final int ONE_VALUE = 1;
    public static final int TWO_VALUE = 2;
    public static final String RANDOM_WEEK_ALL_WEEK_OVERVIEW_LABEL = "Angabe zur Randomisierung in alle Wochen anzeigen";
    public static final String RANDOM_WEEK_ONE_OVERVIEW_OPTION = "Angaben zur Randomisierung in der " + ONE_VALUE + ". Woche anzeigen";
    public static final String RANDOM_WEEK_TWO_OVERVIEW_OPTION = "Angaben zur Randomisierung in der " +  TWO_VALUE + ". Woche anzeigen";
    public static final String RANDOM_WEEK_ONE_GERMANY_OVERVIEW_OPTION = "Anzahl der Patienten pro Zentrum in " + GERMANY + " in der " + ONE_VALUE + ". Woche anzeigen";
    public static final String RANDOM_WEEK_ONE_ENGLAND_OVERVIEW_OPTION = "Anzahl der Patienten pro Zentrum in " + ENGLAND + " in der " + ONE_VALUE + ". Woche anzeigen";
    public static final String RANDOM_WEEK_TWO_GERMANY_OVERVIEW_OPTION = "Anzahl der Patienten pro Zentrum in " + GERMANY + " in der " + TWO_VALUE + ". Woche anzeigen";
    public static final String RANDOM_WEEK_TWO_ENGLAND_OVERVIEW_OPTION = "Anzahl der Patienten pro Zentrum in " + ENGLAND + " in der " + TWO_VALUE + ". Woche anzeigen";
    public static final String CONSENT_FORM_TXT = "Neue Einwilligung anlegen:";
    public static final String CONSENT_DELETE_BTN_LABEL = "Einwilligung löschen";
    public static final String DATE_LABEL = "Datum: ";
    public static final String CONSENT_TABLE_LABEL = "Angabe zu Einwilligung der Studienteilnehmer";
    public static final String ALL_CONSENT_OPTION = "Alle Einwilligungen";
    public static final String MISSING_CONSENT_OVERVIEW_OPTION = "Liste der Patienten mit fehlende Einwilligung";
    public static final String INCOMPLETE_CONSENT_OVERVIEW_OPTION = "Liste der Patienten mit unvollständigen Einwilligung";
    public static final String INFORMED_CONSENT_AFTER_RANDOMIZATION_OVERVIEW_OPTION = "Liste der Patienten mit erteilten Einwilligung nach der Radomisierung";
    public static final String INFORMED_CONSENT_OVERVIEW_OPTION = "Liste der Patienten mit erteilten Einwilligung";
    public static final String DECLINED_CONSENT_OVERVIEW_OPTION = "Liste der Patienten ohne erteilten Einwilligung";
    public static final String CONSENT_OVERVIEW_FILTER_PROMPT_TXT = "Liste auswählen und anzeigen";
    public static final String CENTER_OVERVIEW_LABEL = "Liste aller vorhandenen Zentren in der Studie";
    public static final String CENTER_OVERVIEW_OPTION = "Liste aller Zentren";
    public static final String GERMAN_CENTER_OVERVIEW_OPTION = "Liste der Zentren in Deutschland";
    public static final String BRITISH_CENTER_OVERVIEW_OPTION = "Liste der Zentren in Großbritannien";
    public static final String GERMAN_CENTER_OVERVIEW_LABEL = "Liste aller vorhandenen deutschen Zentren in der Studie";
    public static final String BRITISH_CENTER_OVERVIEW_LABEL = "Liste aller vorhandenen britischen Zentren in der Studie";
    public static final String INPUT_VALIDATION_ERROR_MSG = "Bitte Eingaben prüfen!";

    private static String formatOption(Country country, int week) {
        return String.format("Anzahl der Patienten pro Zentrum in %s in der %s. Woche anzeigen", country, week);
    }
}
