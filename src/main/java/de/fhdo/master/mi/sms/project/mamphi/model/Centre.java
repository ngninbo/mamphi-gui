package de.fhdo.master.mi.sms.project.mamphi.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Beauclair Dongmo Ngnintedem
 *
 */
public class Centre {

	private SimpleStringProperty monitor;
	private SimpleStringProperty trier;
	private SimpleStringProperty place;
	private SimpleStringProperty country;
	private SimpleIntegerProperty centreID;

	public Centre() {
		super();
	}

	/**
	 * @param monitor Monitor
	 * @param trier Trier
	 * @param place Place
	 * @param Country Country
	 * @param centreID Centre ID
	 */
	public Centre(String monitor, String trier, String place, String Country, int centreID) {
		super();
		this.monitor = new SimpleStringProperty(monitor);
		this.trier = new SimpleStringProperty(trier);
		this.place = new SimpleStringProperty(place);
		this.country = new SimpleStringProperty(Country);
		this.centreID = new SimpleIntegerProperty(centreID);
	}

	public String getMonitor() {
		return this.monitor.get();
	}

	public void setMonitor(String monitor) {
		this.monitor.set(monitor);
	}

	public String getTrier() {
		return this.trier.get();
	}

	public void setTrier(String trier) {
		this.trier.set(trier);
	}

	public String getPlace() {
		return this.place.get();
	}

	public void setPlace(String place) {
		this.place.set(place);
	}

	public String getCountry() {
		return this.country.get();
	}

	public void setCountry(String country) {
		this.country.set(country);
	}

	public int getCentreID() {
		return this.centreID.get();
	}

	public void setCentreID(int centreID) {
		this.centreID.set(centreID);
	}
}
