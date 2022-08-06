package de.fhdo.master.mi.sms.project.mamphi.model;

import de.fhdo.master.mi.sms.project.mamphi.annotation.Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author biocl
 *
 */
@Model
public class RandomizationWeek {

	private SimpleIntegerProperty patientID;
	private SimpleIntegerProperty centre;
	private SimpleStringProperty group;
	private SimpleStringProperty date;

	public RandomizationWeek() {
		super();
	}

	/**
	 * @param patientID Patient ID
	 * @param centre Centre ID
	 * @param group Group
	 * @param date Datum
	 */
	public RandomizationWeek(int patientID, int centre, String group, String date) {
		super();
		this.patientID = new SimpleIntegerProperty(patientID);
		this.centre = new SimpleIntegerProperty(centre);
		this.group = new SimpleStringProperty(group);
		this.date = new SimpleStringProperty(date);
	}

	public int getPatientID() {
		return this.patientID.get();
	}

	public void setPatientID(int patientID) {
		this.patientID.set(patientID);
	}

	public int getCentre() {
		return this.centre.get();
	}

	public void setCentre(int centre) {
		this.centre.set(centre);
	}

	public String getGroup() {
		return this.group.get();
	}

	public void setGroup(String group) {
		this.group.set(group);
	}

	public String getDate() {
		return this.date.get();
	}

	public void setDate(final String date) {
		this.date.set(date);
	}
}
