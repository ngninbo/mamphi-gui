/**
 * 
 */
package de.fhdo.master.mi.sms.project.mamphi.model;

import java.time.LocalDate;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * @author biocl
 *
 */
public class MonitorVisite extends Zentrum {

	private SimpleIntegerProperty numberOfPatient;
	private SimpleListProperty<LocalDate> visiteDate;

	/**
	 * 
	 */
	public MonitorVisite() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param monitor Monitor
	 * @param pruefer Prüfer
	 * @param ort Ort
	 * @param land Land
	 * @param zentrum_id Zentrum ID
	 */
	public MonitorVisite(String monitor, String pruefer, String ort, String land, int zentrum_id) {
		super(monitor, pruefer, ort, land, zentrum_id);
		// TODO Auto-generated constructor stub
	}

	public MonitorVisite(Zentrum center) {
		this(center.getMonitor(), center.getPruefer(), center.getOrt(), center.getLand(), center.getZentrumID());
	}
	
	public MonitorVisite(Zentrum center, int numberOfPatient, List<LocalDate> visites) {
		this(center);
		this.numberOfPatient = new SimpleIntegerProperty(numberOfPatient);
		this.setVisiteDate(visites);
	}

	public MonitorVisite(int zentrum_Id, String land, String ort, String pruefer, String monitor, int gesamtanzahl) {
		super(monitor, pruefer, ort, land, zentrum_Id);
		this.numberOfPatient = new SimpleIntegerProperty(gesamtanzahl);
	}
	
	public int getNumberOfPatient() {
		return this.numberOfPatient.get();
	}
	
	public void setNumberOfPatient(int numberOfPatient) {
		this.numberOfPatient.set(numberOfPatient);
	}
	
	public List<LocalDate> getVisiteDate() {
		return this.visiteDate.get();
	}
	
	public void setVisiteDate(List<LocalDate> visiteDate) {
		this.visiteDate = (visiteDate !=null) ? new SimpleListProperty<>(FXCollections.observableArrayList(visiteDate)): null;
	}		
}
