package de.fhdo.master.mi.sms.project.mamphi.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

/**
 * @author Beauclair Dongmo Ngnintedem
 *
 */
public class InformedConsent {

	private SimpleIntegerProperty patientID;
	private SimpleIntegerProperty centreID;
	private SimpleStringProperty consent;
	private SimpleStringProperty date;

	/**
	 * 
	 */
	public InformedConsent() {
		super();
	}

	/**
	 * @param patientID Patient id
	 * @param centreID Center id
	 * @param consent Consent
	 * @param date Date
	 */
	public InformedConsent(int patientID, int centreID, String consent, String date) {
		super();
		this.patientID = new SimpleIntegerProperty(patientID);
		this.centreID = new SimpleIntegerProperty(centreID);
		this.consent = new SimpleStringProperty(consent);
		this.date = new SimpleStringProperty(date);
	}

	public int getPatientID() {
		return this.patientID.get();
	}

	public void setPatientID(int patientID) {
		this.patientID.set(patientID);
	}

	public int getCentreID() {
		return this.centreID.get();
	}

	public void setCentreID(int zentrumID) {
		this.centreID.set(zentrumID);
	}

	public String getConsent() {
		return this.consent.get();
	}

	public void setConsent(String consent) {
		this.consent.set(consent);
	}

	public String getDate() {
		return this.date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InformedConsent)) return false;
		InformedConsent that = (InformedConsent) o;
		return Objects.equals(getPatientID(), that.getPatientID()) &&
				Objects.equals(getCentreID(), that.getCentreID()) && Objects.equals(getConsent(), that.getConsent())
				&& Objects.equals(getDate(), that.getDate());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPatientID(), getCentreID(), getConsent(), getDate());
	}

	@Override
	public String toString() {
		return "InformedConsent [patientID=" + patientID + ", centreID=" + centreID + ", consent="
				+ consent + ", date=" + date + "]";
	}
}
