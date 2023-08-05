package de.fhdo.master.mi.sms.project.mamphi.model;

/**
 * @author Beauclair Dongmo Ngnintedem
 *
 */
public enum Country {
	
	// Germany
	DE ("Deutschland"),
	// England
	GB ("Großbritannien");

	private final String country;

	Country(String country) {
		this.country = country;
	}

	public String getFullCountryName() {
		return country;
	}
}
