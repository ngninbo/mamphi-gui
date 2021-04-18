package de.fhdo.master.mi.sms.project.mamphi.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientCenter {
	
	private SimpleStringProperty center;
	private SimpleIntegerProperty count;

	public PatientCenter() {
	}

	/**
	 * @param center Zentrum
	 * @param count Anzahl Patienten
	 */
	public PatientCenter(String center, Integer count) {
		super();
		this.center = new SimpleStringProperty(center);
		this.count = new SimpleIntegerProperty(count);
	}
	

	public String getCenter() {
		return this.center.get();
	}
	

	public void setCenter(String center) {
		this.center.set(center);
	}
	

	public int getCount() {
		return this.count.get();
	}
	

	public void setCount(int count) {
		this.count.set(count);
	}
}
