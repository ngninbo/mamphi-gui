package de.fhdo.master.mi.sms.project.mamphi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.fhdo.master.mi.sms.project.mamphi.utils.TrialUtils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.ENGLAND;
import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.GERMANY;

/**
 * @author Beauclair Dongmo Ngnintedem
 *
 */
public class Zentrum {

	private SimpleStringProperty monitor;
	private SimpleStringProperty pruefer;
	private SimpleStringProperty ort;
	private SimpleStringProperty land;
	private SimpleIntegerProperty zentrumID;

	public Zentrum() {
		super();
	}

	/**
	 * @param monitor Monitor
	 * @param pruefer Prüfer
	 * @param ort Ort
	 * @param land Land
	 * @param zentrumID Zentrum ID
	 */
	public Zentrum(String monitor, String pruefer, String ort, String land, int zentrumID) {
		super();
		this.monitor = new SimpleStringProperty(monitor);
		this.pruefer = new SimpleStringProperty(pruefer);
		this.ort = new SimpleStringProperty(ort);
		this.land = new SimpleStringProperty(land);
		this.zentrumID = new SimpleIntegerProperty(zentrumID);
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

	public int getZentrumID() {
		return this.zentrumID.get();
	}

	public void setZentrumID(int zentrumID) {
		this.zentrumID.set(zentrumID);
	}
}
