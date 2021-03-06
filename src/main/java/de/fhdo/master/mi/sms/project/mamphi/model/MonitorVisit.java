package de.fhdo.master.mi.sms.project.mamphi.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.*;
import static de.fhdo.master.mi.sms.project.mamphi.utils.GuiConstants.FIVE;

/**
 * @author Beauclair Dongmo Ngnintedem
 *
 */
public class MonitorVisit extends Centre {

	private SimpleIntegerProperty numberOfPatient;
	private SimpleListProperty<LocalDate> visitDate;

	public MonitorVisit() {
		super();
	}

	/**
	 * @param monitor Monitor
	 * @param trier Trier
	 * @param ort Ort
	 * @param land Land
	 * @param centreId Centre ID
	 */
	public MonitorVisit(String monitor, String trier, String ort, String land, int centreId) {
		super(monitor, trier, ort, land, centreId);
		// TODO Auto-generated constructor stub
	}

	public MonitorVisit(Centre center) {
		this(center.getMonitor(), center.getTrier(), center.getPlace(), center.getCountry(), center.getCentreID());
	}

	public MonitorVisit(Centre center, int numberOfPatient, List<LocalDate> visites) {
		this(center);
		this.numberOfPatient = new SimpleIntegerProperty(numberOfPatient);
		this.setVisitDates(visites);
	}

	public MonitorVisit(int zentrum_Id, String land, String ort, String pruefer, String monitor, int gesamtanzahl) {
		super(monitor, pruefer, ort, land, zentrum_Id);
		this.numberOfPatient = new SimpleIntegerProperty(gesamtanzahl);
	}

	public int getNumberOfPatient() {
		return this.numberOfPatient.get();
	}
	
	public void setNumberOfPatient(int numberOfPatient) {
		this.numberOfPatient.set(numberOfPatient);
	}
	
	public List<LocalDate> getVisitDate() {
		return this.visitDate.get();
	}

	public void setVisitDates() {

		List<LocalDate> listOfVisiteDates;
		final int numberPatient = getNumberOfPatient();

		if (numberPatient > MIN_NUM_PATIENT_FOR_MONTHLY_VISIT) {
			listOfVisiteDates = START_DATE.datesUntil(END_DATE, Period.ofMonths(1)).collect(Collectors.toList());
			setVisitDates(listOfVisiteDates.subList(0, FIVE));
		}
		else if (numberPatient > FOUR && numberPatient < MIN_NUM_PATIENT_FOR_MONTHLY_VISIT) {
			listOfVisiteDates = START_DATE.datesUntil(END_DATE, Period.ofMonths(2)).collect(Collectors.toList());
			setVisitDates(listOfVisiteDates.subList(0, FIVE));
		}
		else if (numberPatient > 0 && numberPatient < FIVE) {
			listOfVisiteDates = START_DATE.datesUntil(END_DATE, Period.ofMonths(3)).collect(Collectors.toList());
			setVisitDates(listOfVisiteDates.subList(0, FIVE));
		}
		else {
			listOfVisiteDates = new ArrayList<>();
			setVisitDates(listOfVisiteDates);
		}
	}

	private void setVisitDates(List<LocalDate> visitDate) {
		this.visitDate = (visitDate !=null) ? new SimpleListProperty<>(FXCollections.observableArrayList(visitDate)): null;
	}
}
