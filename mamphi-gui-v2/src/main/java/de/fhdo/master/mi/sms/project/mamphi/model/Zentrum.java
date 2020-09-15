/**
 * 
 */
package de.fhdo.master.mi.sms.project.mamphi.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author biocl
 *
 */
public class Zentrum {

	private static final int incrementor = 1;
	private static int maxId = 0;
	private SimpleStringProperty monitor;
	private SimpleStringProperty pruefer;
	private SimpleStringProperty ort;
	private SimpleStringProperty land;
	private SimpleIntegerProperty zentrum_id;

	/**
	 * 
	 */
	public Zentrum() {
		super();
	}

	/**
	 * @param monitor Monitor
	 * @param pruefer Prüfer
	 * @param ort Ort
	 * @param land Land
	 * @param zentrum_id Zentrum ID
	 */
	public Zentrum(String monitor, String pruefer, String ort, String land, int zentrum_id) {
		super();
		this.monitor = new SimpleStringProperty(monitor);
		this.pruefer = new SimpleStringProperty(pruefer);
		this.ort = new SimpleStringProperty(ort);
		this.land = new SimpleStringProperty(land);
		this.zentrum_id = new SimpleIntegerProperty(zentrum_id);
	}

	/**
	 * Construct a new center using required attributes and compute the id manually
	 * 
	 * @param monitor    Monitor
	 * @param pruefer    Pruefer
	 * @param ort        Ort
	 * @param land       Land
	 * @param centerList List of current center
	 */
	public Zentrum(String monitor, String pruefer, String ort, String land, List<Zentrum> centerList) {
		super();
		this.monitor = new SimpleStringProperty(monitor);
		this.pruefer = new SimpleStringProperty(pruefer);
		this.ort = new SimpleStringProperty(ort);
		this.land = new SimpleStringProperty(land);
		this.zentrum_id = new SimpleIntegerProperty(this.getZentrum_id(land, centerList));
	}

	public String getMonitor() {
		return this.monitor.get();
	}

	public void setMonitor(String monitor) {
		this.monitor.set(monitor);
	}

	public String getPruefer() {
		return this.pruefer.get();
	}

	public void setPruefer(String pruefer) {
		this.pruefer.set(pruefer);
	}

	public String getOrt() {
		return this.ort.get();
	}

	public void setOrt(String ort) {
		this.ort.set(ort);
	}

	public String getLand() {
		return this.land.get();
	}

	public void setLand(String land) {
		this.land.set(land);
	}

	public int getZentrum_id() {
		return this.zentrum_id.get();
	}

	public void setZentrum_id(int zentrum_id) {
		this.zentrum_id.set(zentrum_id);
	}

	private int getZentrum_id(String land, List<Zentrum> centerList) {

		List<Zentrum> centerInD = new ArrayList<>();
		List<Zentrum> centerInGB = new ArrayList<>();

		for (Zentrum center : centerList) {
			if (center.getLand().equals("Deutschland")) {
				centerInD.add(center);
			} else {
				centerInGB.add(center);
			}
		}

		if (land.equals("Deutschland")) {
			for (Zentrum center : centerInD) {

				if (center.getZentrum_id() > maxId) {
					maxId = center.getZentrum_id();
				}
			}
		} else {
			for (Zentrum center : centerInGB) {

				if (center.getZentrum_id() > maxId) {
					maxId = center.getZentrum_id();
				}
			}
		}
		return maxId + incrementor;
	}
}
