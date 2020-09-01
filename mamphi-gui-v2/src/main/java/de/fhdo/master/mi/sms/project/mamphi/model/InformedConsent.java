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
	
	private SimpleIntegerProperty patient_id;
	private SimpleIntegerProperty zentrum_id;
	private SimpleStringProperty einwilligung;
	private SimpleStringProperty date;
	/**
	 * 
	 */
	public InformedConsent() {
		super();
	}
	/**
	 * @param patient_id
	 * @param zentrum_id
	 * @param einwilligung
	 * @param date
	 */
	public InformedConsent(int patient_id, int zentrum_id,
			String einwilligung, String date) {
		super();
		this.patient_id = new SimpleIntegerProperty(patient_id);
		this.zentrum_id = new SimpleIntegerProperty(zentrum_id);
		this.einwilligung = new SimpleStringProperty(einwilligung);
		this.date = new SimpleStringProperty(date);
	}
	
	
	public int getPatient_id() {
		return this.patient_id.get();
	}
	
	public void setPatient_id(int patient_id) {
		this.patient_id.set(patient_id);
	}
	
	
	public int getZentrum_id() {
		return this.zentrum_id.get();
	}
	
	public void setZentrum_id(int zentrum_id) {
		this.zentrum_id.set(zentrum_id);
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
		return "InformedConsent [patient_id=" + patient_id + ", zentrum_id=" + zentrum_id + ", einwilligung="
				+ einwilligung + ", date=" + date + "]";
	}
	
	
}
