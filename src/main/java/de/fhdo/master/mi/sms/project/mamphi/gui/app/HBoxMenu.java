package de.fhdo.master.mi.sms.project.mamphi.gui.app;

public class HBoxMenu {

    private final ConsentHBox consentHBox;
    private final RandomizationHBox randomizationHBox;
    private final CentreHBox centreHBox;
    private final MonitoringPlanHBox monitoringPlanHBox;

    public HBoxMenu(Main main) {
        this.centreHBox = new CentreHBox(main);
        this.consentHBox = new ConsentHBox(main);
        this.randomizationHBox = new RandomizationHBox(main);
        this.monitoringPlanHBox = new MonitoringPlanHBox(main);
    }

    public ConsentHBox getConsentHBox() {
        return consentHBox;
    }

    public RandomizationHBox getRandomizationHBox() {
        return randomizationHBox;
    }

    public CentreHBox getCentreHBox() {
        return centreHBox;
    }

    public MonitoringPlanHBox getMonitoringPlanHBox() {
        return monitoringPlanHBox;
    }
}
