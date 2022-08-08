package de.fhdo.master.mi.sms.project.mamphi.model;

import java.time.LocalDate;
import java.time.Period;
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
	 * @param centerId centre id
	 * @param country Country
	 * @param place Place
	 * @param trier Trier
	 * @param monitor Monitor
	 * @param numOfPatient Number of patient in centre
	 */
	public MonitorVisit(int centerId, String country, String place, String trier, String monitor, int numOfPatient) {
		super(monitor, trier, place, country, centerId);
		this.numberOfPatient = new SimpleIntegerProperty(numOfPatient);
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

		final int numberPatient = getNumberOfPatient();

		if (numberPatient > MIN_NUM_PATIENT_FOR_MONTHLY_VISIT) {
			setVisitDates(nextFiveVisitDates(1));
		}
		else if (numberPatient > FOUR && numberPatient < MIN_NUM_PATIENT_FOR_MONTHLY_VISIT) {
			setVisitDates(nextFiveVisitDates(2));
		}
		else if (numberPatient > 0 && numberPatient < FIVE) {
			setVisitDates(nextFiveVisitDates(3));
		}
		else {
			setVisitDates(List.of());
		}
	}

	private void setVisitDates(List<LocalDate> visitDate) {
		this.visitDate = !visitDate.isEmpty() ? new SimpleListProperty<>(FXCollections.observableArrayList(visitDate)): null;
	}

	private List<LocalDate> nextFiveVisitDates(int periodOfMonths) {
		return START_DATE.datesUntil(END_DATE, Period.ofMonths(periodOfMonths))
				.limit(FIVE)
				.collect(Collectors.toList());
	}
}
