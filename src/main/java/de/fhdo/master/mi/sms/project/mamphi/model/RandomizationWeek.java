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
public class RandomizationWeek {

	private SimpleIntegerProperty patient_id;
	private SimpleIntegerProperty zentrum;
	private SimpleStringProperty behandlungsarm;
	private SimpleStringProperty date;

	/**
	 * 
	 */
	public RandomizationWeek() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param patient_id Patient ID
	 * @param zentrum Zentrum ID
	 * @param behandlungsarm Behandlungsarm
	 * @param date Datum
	 */
	public RandomizationWeek(int patient_id, int zentrum, String behandlungsarm, String date) {
		super();
		this.patient_id = new SimpleIntegerProperty(patient_id);
		this.zentrum = new SimpleIntegerProperty(zentrum);
		this.behandlungsarm = new SimpleStringProperty(behandlungsarm);
		this.date = new SimpleStringProperty(date);
	}

	public int getPatient_id() {
		return this.patient_id.get();
	}

	public void setPatient_id(int patient_id) {
		this.patient_id.set(patient_id);
	}

	public int getZentrum() {
		return this.zentrum.get();
	}

	public void setZentrum(int zentrum) {
		this.zentrum.set(zentrum);
	}

	public String getBehandlungsarm() {
		return this.behandlungsarm.get();
	}

	public void setBehandlungsarm(String behandlungsarm) {
		this.behandlungsarm.set(behandlungsarm);
	}

	public String getDate() {
		return this.date.get();
	}

	public void setDate(final String date) {
		this.date.set(date);
	}
}
