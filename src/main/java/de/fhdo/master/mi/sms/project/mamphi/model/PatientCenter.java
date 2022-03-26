package de.fhdo.master.mi.sms.project.mamphi.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientCenter {
	
	private SimpleStringProperty centre;
	private SimpleIntegerProperty count;

	public PatientCenter() {
		super();
	}

	/**
	 * @param centre Centre
	 * @param count Number Of Patient
	 */
	public PatientCenter(String centre, Integer count) {
		super();
		this.centre = new SimpleStringProperty(centre);
		this.count = new SimpleIntegerProperty(count);
	}
	

	public String getCentre() {
		return this.centre.get();
	}
	

	public void setCentre(String centre) {
		this.centre.set(centre);
	}
	

	public int getCount() {
		return this.count.get();
	}
	

	public void setCount(int count) {
		this.count.set(count);
	}
}
