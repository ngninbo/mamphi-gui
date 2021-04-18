/**
 * 
 */
package de.fhdo.master.mi.sms.project.mamphi.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author biocl
 *
 */
public class InformedConsent {

	private SimpleIntegerProperty patientenID;
	private SimpleIntegerProperty zentrumID;
	private SimpleStringProperty einwilligung;
	private SimpleStringProperty date;

	/**
	 * 
	 */
	public InformedConsent() {
		super();
	}

	/**
	 * @param patientenID
	 * @param zentrumID
	 * @param einwilligung
	 * @param date
	 */
	public InformedConsent(int patientenID, int zentrumID, String einwilligung, String date) {
		super();
		this.patientenID = new SimpleIntegerProperty(patientenID);
		this.zentrumID = new SimpleIntegerProperty(zentrumID);
		this.einwilligung = new SimpleStringProperty(einwilligung);
		this.date = new SimpleStringProperty(date);
	}

	public int getPatientenID() {
		return this.patientenID.get();
	}

	public void setPatientenID(int patientenID) {
		this.patientenID.set(patientenID);
	}

	public int getZentrumID() {
		return this.zentrumID.get();
	}

	public void setzentrumID(int zentrumID) {
		this.zentrumID.set(zentrumID);
	}

	public String getEinwilligung() {
		return this.einwilligung.get();
	}

	public void setEinwilligung(String einwilligung) {
		this.einwilligung.set(einwilligung);
	}

	public String getDate() {
		return this.date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	@Override
	public String toString() {
		return "InformedConsent [patientenID=" + patientenID + ", zentrumID=" + zentrumID + ", einwilligung="
				+ einwilligung + ", date=" + date + "]";
	}
}
