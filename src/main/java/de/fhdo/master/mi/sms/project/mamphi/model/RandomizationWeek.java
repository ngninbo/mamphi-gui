package de.fhdo.master.mi.sms.project.mamphi.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author biocl
 *
 */
public class RandomizationWeek {

	private SimpleIntegerProperty patientenID;
	private SimpleIntegerProperty zentrum;
	private SimpleStringProperty behandlungsarm;
	private SimpleStringProperty date;

	public RandomizationWeek() {
		super();
	}

	/**
	 * @param patientenID Patient ID
	 * @param zentrum Zentrum ID
	 * @param behandlungsarm Behandlungsarm
	 * @param date Datum
	 */
	public RandomizationWeek(int patientenID, int zentrum, String behandlungsarm, String date) {
		super();
		this.patientenID = new SimpleIntegerProperty(patientenID);
		this.zentrum = new SimpleIntegerProperty(zentrum);
		this.behandlungsarm = new SimpleStringProperty(behandlungsarm);
		this.date = new SimpleStringProperty(date);
	}

	public int getPatientenID() {
		return this.patientenID.get();
	}

	public void setPatientenID(int patientenID) {
		this.patientenID.set(patientenID);
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
