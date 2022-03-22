package de.fhdo.master.mi.sms.project.mamphi.utils;

import java.time.LocalDate;

public class GuiConstants {

    public static final int MAIN_SCENE_HEIGHT = 999;
    public static final int VALUE_MISSING = -MAIN_SCENE_HEIGHT;
    public static final int FONT = 20;
    public static final String FONT_NAME = "Arial";
    public static final int INSETS_VALUE = 10;
    public static final int LOGIN_SCENE_GRID_DIM = 400;
    public static final int MAIN_SCENE_WIDTH = 600;
    public static final int MIN_WIDTH = 200;
    public static final int INSETS_MIN_VALUE = 0;
    public static final int SPACING_DEFAULT_VALUE = 5;
    public static final int SPACING_MIN_VALUE = 15;
    public static final int SPACING_MAX_VALUE = 30;
    public static final int DEFAULT_MIN_WIDTH = 100;
    public static final int VISIT_COL_PREF_WIDTH = 380;
    public static final int H_BOX_SPACING_VALUE = 25;
    public static final int PADDING_BOTTOM_VALUE = 150;
    public static final int TRIAL_YEAR = 2019;
    public static final int TRIAL_MONTH = 6;
    public static final int MIN_NUM_PATIENT_FOR_MONTHLY_VISIT = 10;
    public static final int FIVE = 5;
    public static final int FOUR = 4;
    public static final LocalDate START_DATE = LocalDate.of(TRIAL_YEAR, TRIAL_MONTH, 1);
    public static final LocalDate END_DATE = START_DATE.plusYears(2);
}
